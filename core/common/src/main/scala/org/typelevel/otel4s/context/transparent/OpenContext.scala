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

import cats.effect.SyncIO

import scala.reflect.ClassTag

final case class OpenContext private[transparent] (
    contents: Map[TypedKey[_], Any]
) extends Context {
  type Self = OpenContext
  type Key[A] = TypedKey[A]
  type KeyBounds[A] = ClassTag[A]

  def get[A](key: TypedKey[A]): Option[A] =
    contents.get(key).map(_.asInstanceOf[A])
  def updated[A](key: TypedKey[A], value: A): OpenContext =
    new OpenContext(contents.updated(key, value))
}

object OpenContext extends OpenContextProvider[SyncIO]
