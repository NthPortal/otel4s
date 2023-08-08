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
package transparent

import cats.effect.Sync

import scala.reflect.ClassTag

final class TypedKey[A] private (val name: String)(implicit
    val classTag: ClassTag[A]
) extends Key[A] {
  override def toString: String =
    s"Key[${TypedKey.renderType(classTag.runtimeClass)}]($name)"
}

private[transparent] object TypedKey {
  def unique[F[_]: Sync, A: ClassTag](name: String): F[TypedKey[A]] =
    Sync[F].delay(new TypedKey(name))

  private def renderType(cls: Class[_]): String = {
    if (cls.isArray) s"Array[${renderType(cls.getComponentType)}]"
    else if (cls == classOf[AnyRef]) "AnyRef|Any"
    else cls.getName
  }
}
