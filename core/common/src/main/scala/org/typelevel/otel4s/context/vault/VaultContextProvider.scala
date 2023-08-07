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
package vault

import cats.Functor
import cats.effect.Unique
import cats.syntax.functor._
import org.typelevel.vault.{Key => VKey}
import org.typelevel.vault.Vault

class VaultContextProvider[F[_]: Functor: Unique] private[vault]
    extends ContextProvider[F, VaultContext] {
  def root: VaultContext = VaultContextProvider.rootContext
  def uniqueKey[A: Key.NoBounds](name: String): F[VaultContext.Key[A]] =
    VKey.newKey[F, A].map(new VaultContext.Key[A](name, _))
}

object VaultContextProvider {
  private val rootContext: VaultContext = VaultContext(Vault.empty)

  def get[F[_]: Functor: Unique]: VaultContextProvider[F] =
    new VaultContextProvider[F]
}
