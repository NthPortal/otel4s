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

package org.typelevel.otel4s
package context

trait Context[C] {
  type Key[A] <: context.Key[A]
  type KeyCreationBounds[_[_]]
  type KeyTypeBounds[_]

  def get[A](ctx: C)(key: Key[A]): Option[A]

  def getOrElse[A](ctx: C)(key: Key[A], default: => A): A =
    get(ctx)(key).getOrElse(default)

  def updated[A](ctx: C)(key: Key[A], value: A): C

  def root: C

  def uniqueKey[F[_]: KeyCreationBounds, A: KeyTypeBounds](
      name: String
  ): F[Key[A]]
}

object Context {
  def apply[C](implicit c: Context[C]): Context[C] = c

  final class Ops[C](context: C)(implicit val c: Context[C]) {
    def get[A](key: c.Key[A]): Option[A] =
      c.get(context)(key)
    def getOrElse[A](key: c.Key[A], default: => A): A =
      c.getOrElse(context)(key, default)
    def updated[A](key: c.Key[A], value: A): C =
      c.updated(context)(key, value)
  }
}
