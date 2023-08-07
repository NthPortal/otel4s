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

trait Context {
  type Self <: Context
  type Key[A] <: context.Key[A]
  type KeyBounds[_]

  def get[A](key: Key[A]): Option[A]

  def getOrElse[A](key: Key[A], default: => A): A =
    get(key).getOrElse(default)

  def updated[A](key: Key[A], value: A): Self
}
