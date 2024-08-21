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

package org.typelevel.otel4s.semconv.experimental.attributes

import org.typelevel.otel4s.AttributeKey
import org.typelevel.otel4s.AttributeKey._

// DO NOT EDIT, this is an Auto-generated file from buildscripts/semantic-convention/templates/SemanticAttributes.scala.j2
object EnduserExperimentalAttributes {

  /** Deprecated, use `user.id` instead.
    */
  @deprecated("Use `user.id` instead", "0.5.0")
  val EnduserId: AttributeKey[String] = string("enduser.id")

  /** Deprecated, use `user.roles` instead.
    */
  @deprecated("Use `user.roles` instead", "0.5.0")
  val EnduserRole: AttributeKey[String] = string("enduser.role")

  /** Deprecated, no replacement at this time.
    */
  @deprecated("No replacement at this time", "0.5.0")
  val EnduserScope: AttributeKey[String] = string("enduser.scope")

}
