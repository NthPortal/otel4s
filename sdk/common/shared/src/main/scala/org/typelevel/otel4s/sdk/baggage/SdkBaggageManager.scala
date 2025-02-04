/*
 * Copyright 2023 Typelevel
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

package org.typelevel.otel4s.sdk.baggage

import cats.Applicative
import cats.effect.SyncIO
import org.typelevel.otel4s.baggage.Baggage
import org.typelevel.otel4s.baggage.BaggageManager
import org.typelevel.otel4s.sdk.context.Context
import org.typelevel.otel4s.sdk.context.LocalContext

private final class SdkBaggageManager[F[_]](implicit localContext: LocalContext[F]) extends BaggageManager[F] {
  import SdkBaggageManager._

  def applicative: Applicative[F] = localContext.applicative
  def ask[E2 >: Baggage]: F[E2] =
    localContext.reader(baggageFromContext)
  def local[A](fa: F[A])(f: Baggage => Baggage): F[A] =
    localContext.local(fa) { ctx =>
      ctx.updated(BaggageKey, f(baggageFromContext(ctx)))
    }
}

private[sdk] object SdkBaggageManager {
  val BaggageKey: Context.Key[Baggage] =
    Context.Key
      .unique[SyncIO, Baggage]("otel4s-baggage-key")
      .unsafeRunSync()

  private def baggageFromContext(context: Context): Baggage =
    context.getOrElse(BaggageKey, Baggage.empty)

  def fromLocal[F[_]: LocalContext]: BaggageManager[F] =
    new SdkBaggageManager[F]
}
