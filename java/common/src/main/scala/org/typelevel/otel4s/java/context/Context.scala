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

import cats.effect.SyncIO
import io.opentelemetry.api.trace.{Span => JSpan}
import io.opentelemetry.context.{Context => JContext}
import io.opentelemetry.context.ContextKey
import org.typelevel.otel4s.context.{Context => CoreContext}
import org.typelevel.otel4s.context.{Key => CoreKey}

sealed trait Context extends CoreContext {
  type Self = Context
  type Key[A] = Context.Key[A]
  type KeyBounds[A] = CoreKey.NoBounds[A]

  def underlying: JContext
  private[java] def map(f: JContext => JContext): Context
}

object Context extends ContextProvider[SyncIO] {
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

  def wrap(context: JContext): Context = {
    val isNoop =
      Option(JSpan.fromContextOrNull(context))
        .exists(!_.getSpanContext.isValid)
    if (isNoop) Noop else Wrapped(context)
  }

  final class Key[A] private[context] (val name: String)
      extends CoreKey[A]
      with ContextKey[A]
}
