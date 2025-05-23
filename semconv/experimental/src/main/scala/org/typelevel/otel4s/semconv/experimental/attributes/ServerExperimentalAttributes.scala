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

package org.typelevel.otel4s
package semconv
package experimental.attributes

// DO NOT EDIT, this is an Auto-generated file from buildscripts/templates/registry/otel4s/attributes/SemanticAttributes.scala.j2
object ServerExperimentalAttributes {

  /** Server domain name if available without reverse DNS lookup; otherwise, IP address or Unix domain socket name.
    *
    * @note
    *   <p> When observed from the client side, and when communicating through an intermediary, `server.address` SHOULD
    *   represent the server address behind any intermediaries, for example proxies, if it's available.
    */
  @deprecated(
    "use `org.typelevel.otel4s.semconv.attributes.ServerAttributes.ServerAddress` instead.",
    ""
  )
  val ServerAddress: AttributeKey[String] =
    AttributeKey("server.address")

  /** Server port number.
    *
    * @note
    *   <p> When observed from the client side, and when communicating through an intermediary, `server.port` SHOULD
    *   represent the server port behind any intermediaries, for example proxies, if it's available.
    */
  @deprecated(
    "use `org.typelevel.otel4s.semconv.attributes.ServerAttributes.ServerPort` instead.",
    ""
  )
  val ServerPort: AttributeKey[Long] =
    AttributeKey("server.port")

}
