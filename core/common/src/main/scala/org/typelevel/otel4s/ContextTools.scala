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

import org.typelevel.otel4s.context.Context
import org.typelevel.otel4s.context.ContextProvider

trait ContextTools[F[_], C <: Context] {
  def propagators: ContextPropagators[F, C]

  def provider: ContextProvider[F, C]
}

object ContextTools {
  private[this] final case class Impl[F[_], C <: Context](
      propagators: ContextPropagators[F, C],
      provider: ContextProvider[F, C]
  ) extends ContextTools[F, C]

  def apply[F[_], C <: Context](
      propagators: ContextPropagators[F, C],
      provider: ContextProvider[F, C]
  ): ContextTools[F, C] =
    Impl(propagators, provider)
}
