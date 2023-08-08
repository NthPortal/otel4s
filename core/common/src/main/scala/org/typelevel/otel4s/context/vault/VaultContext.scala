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
package vault

import cats.Functor
import cats.effect.Unique
import cats.syntax.functor._
import org.typelevel.vault.{Key => VaultKey}
import org.typelevel.vault.Vault

final case class VaultContext(vault: Vault) {
  def get[A](key: VaultContext.Key[A]): Option[A] =
    vault.lookup(key.underlying)
  def getOrElse[A](key: VaultContext.Key[A], default: => A): A =
    get(key).getOrElse(default)
  def updated[A](key: VaultContext.Key[A], value: A): VaultContext =
    VaultContext(vault.insert(key.underlying, value))
}

object VaultContext extends (Vault => VaultContext) {
  final class Key[A] private (
      val name: String,
      private[VaultContext] val underlying: VaultKey[A]
  ) extends context.Key[A]
  object Key {
    def unique[F[_]: Functor: Unique, A](name: String): F[Key[A]] =
      VaultKey.newKey[F, A].map(new Key[A](name, _))
  }

  val root: VaultContext = apply(Vault.empty)

  final case class UniqueFunctor[F[_]](functor: Functor[F], unique: Unique[F])
  object UniqueFunctor {
    implicit def summon[F[_]: Functor: Unique]: UniqueFunctor[F] =
      apply(implicitly, implicitly)
  }

  implicit object Def extends Context[VaultContext] {
    type Key[A] = VaultContext.Key[A]
    type KeyCreationBounds[F[_]] = UniqueFunctor[F]
    type KeyTypeBounds[A] = context.Key.NoBounds[A]

    def get[A](ctx: VaultContext)(key: Key[A]): Option[A] =
      ctx.get(key)
    def updated[A](ctx: VaultContext)(key: Key[A], value: A): VaultContext =
      ctx.updated(key, value)
    def root: VaultContext = VaultContext.root
    def uniqueKey[F[_]: UniqueFunctor, A: context.Key.NoBounds](
        name: String
    ): F[Key[A]] = {
      val uf = implicitly[UniqueFunctor[F]]
      implicit def functor: Functor[F] = uf.functor
      implicit def unique: Unique[F] = uf.unique
      VaultContext.Key.unique(name)
    }
  }
}
