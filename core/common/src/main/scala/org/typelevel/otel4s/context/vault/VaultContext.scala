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

import cats.effect.SyncIO
import org.typelevel.otel4s.context.{Key => CoreKey}
import org.typelevel.vault.{Key => VKey}
import org.typelevel.vault.Vault

final case class VaultContext(vault: Vault) extends Context {
  type Self = VaultContext
  type Key[A] = VaultContext.Key[A]
  type KeyBounds[A] = CoreKey.NoBounds[A]

  def get[A](key: VaultContext.Key[A]): Option[A] =
    vault.lookup(key.underlying)
  def updated[A](key: VaultContext.Key[A], value: A): VaultContext =
    VaultContext(vault.insert(key.underlying, value))
}

object VaultContext
    extends VaultContextProvider[SyncIO]
    with (Vault => VaultContext) {
  final class Key[A] private[vault] (
      val name: String,
      private[VaultContext] val underlying: VKey[A]
  ) extends CoreKey[A]
}
