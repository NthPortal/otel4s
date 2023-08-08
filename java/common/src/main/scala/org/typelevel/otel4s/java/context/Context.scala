/*
 * Copyright 2022 Typelevel
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.typelevel.otel4s.java.context

import cats.effect.Sync
import io.opentelemetry.api.trace.{Span => JSpan}
import io.opentelemetry.context.{Context => JContext}
import io.opentelemetry.context.ContextKey
import org.typelevel.otel4s.context.{Context => ContextDef}
import org.typelevel.otel4s.context.{Key => CoreKey}

sealed trait Context {
  def underlying: JContext
  def get[A](key: Context.Key[A]): Option[A]
  final def getOrElse[A](key: Context.Key[A], default: => A): A =
    get(key).getOrElse(default)
  def updated[A](key: Context.Key[A], value: A): Context
  private[java] def map(f: JContext => JContext): Context
}

object Context {
  private[java] object Noop extends Context {
    val underlying: JContext =
      JSpan.getInvalid.storeInContext(root.underlying)
    def get[A](key: Context.Key[A]): Option[A] = None
    def updated[A](key: Context.Key[A], value: A): Context = this
    private[java] def map(f: JContext => JContext): Context = this
  }

  private[java] final case class Wrapped private[Context] (underlying: JContext)
      extends Context {
    def get[A](key: Context.Key[A]): Option[A] =
      Option(underlying.get(key))
    def updated[A](key: Context.Key[A], value: A): Context =
      Wrapped(underlying.`with`(key, value))
    private[java] def map(f: JContext => JContext): Context =
      wrap(f(underlying))
  }

  final class Key[A] private (val name: String)
      extends CoreKey[A]
      with ContextKey[A]
  object Key {
    def unique[F[_]: Sync, A](name: String): F[Key[A]] =
      Sync[F].delay(new Key(name))
  }

  def wrap(context: JContext): Context = {
    val isNoop =
      Option(JSpan.fromContextOrNull(context))
        .exists(!_.getSpanContext.isValid)
    if (isNoop) Noop else Wrapped(context)
  }

  val root: Context = wrap(JContext.root())

  implicit object Def extends ContextDef[Context] {
    type Key[A] = Context.Key[A]
    type KeyCreationBounds[F[_]] = Sync[F]
    type KeyTypeBounds[A] = CoreKey.NoBounds[A]

    def get[A](ctx: Context)(key: Key[A]): Option[A] =
      ctx.get(key)
    def updated[A](ctx: Context)(key: Key[A], value: A): Context =
      ctx.updated(key, value)
    def root: Context = Context.root
    def uniqueKey[F[_]: Sync, A: CoreKey.NoBounds](name: String): F[Key[A]] =
      Context.Key.unique(name)
  }
}
