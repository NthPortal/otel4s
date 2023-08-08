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

package org.typelevel.otel4s.context
package transparent

import cats.effect.Sync

import scala.reflect.ClassTag

final case class OpenContext private (contents: Map[TypedKey[_], Any]) {
  def get[A](key: TypedKey[A]): Option[A] =
    contents.get(key).map(_.asInstanceOf[A])
  def getOrElse[A](key: TypedKey[A], default: => A): A =
    get(key).getOrElse(default)
  def updated[A](key: TypedKey[A], value: A): OpenContext =
    OpenContext(contents.updated(key, value))
}

object OpenContext {
  val root: OpenContext = apply(Map.empty)

  implicit object Def extends Context[OpenContext] {
    type Key[A] = TypedKey[A]
    type KeyCreationBounds[F[_]] = Sync[F]
    type KeyTypeBounds[A] = ClassTag[A]

    def get[A](ctx: OpenContext)(key: TypedKey[A]): Option[A] =
      ctx.get(key)
    def updated[A](ctx: OpenContext)(key: TypedKey[A], value: A): OpenContext =
      ctx.updated(key, value)
    def root: OpenContext = OpenContext.root
    def uniqueKey[F[_]: Sync, A: ClassTag](name: String): F[TypedKey[A]] =
      TypedKey.unique(name)
  }
}
