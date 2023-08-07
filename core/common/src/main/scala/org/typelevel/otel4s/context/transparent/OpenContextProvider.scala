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

class OpenContextProvider[F[_]: Sync] private[transparent]
    extends ContextProvider[F, OpenContext] {
  def root: OpenContext = OpenContextProvider.rootContext
  def uniqueKey[A: ClassTag](name: String): F[TypedKey[A]] =
    Sync[F].delay(new TypedKey[A](name))
}

object OpenContextProvider {
  private val rootContext = OpenContext(Map.empty)

  def get[F[_]: Sync]: OpenContextProvider[F] =
    new OpenContextProvider[F]
}
