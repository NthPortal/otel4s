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
import io.opentelemetry.context.{Context => JContext}
import org.typelevel.otel4s.context.{ContextProvider => CoreContextProvider}
import org.typelevel.otel4s.context.Key

class ContextProvider[F[_]: Sync] private[context]
    extends CoreContextProvider[F, Context] {
  def root: Context = ContextProvider.rootContext
  def uniqueKey[A: Key.NoBounds](name: String): F[Context.Key[A]] =
    Sync[F].delay(new Context.Key[A](name))
}

object ContextProvider {
  private val rootContext: Context = Context.wrap(JContext.root())

  def get[F[_]: Sync]: ContextProvider[F] = new ContextProvider[F]
}
