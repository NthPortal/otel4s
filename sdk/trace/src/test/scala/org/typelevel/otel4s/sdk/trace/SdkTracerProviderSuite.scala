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

package org.typelevel.otel4s.sdk.trace

import cats.effect.IO
import cats.effect.IOLocal
import munit.CatsEffectSuite
import org.typelevel.otel4s.sdk.context.Context

class SdkTracerProviderSuite extends CatsEffectSuite {

  test("use noop TracerProvider when span processors aren't configured") {
    IOLocal(Context.root).map(_.asLocal).flatMap { implicit local =>
      for {
        provider <- SdkTracerProvider.builder[IO].build
      } yield assertEquals(provider.toString, "TracerProvider.Noop")
    }
  }

}
