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
object FileExperimentalAttributes {

  /** Time when the file was last accessed, in ISO 8601 format.
    *
    * @note
    *   <p> This attribute might not be supported by some file systems — NFS, FAT32, in embedded OS, etc.
    */
  val FileAccessed: AttributeKey[String] =
    AttributeKey("file.accessed")

  /** Array of file attributes.
    *
    * @note
    *   <p> Attributes names depend on the OS or file system. Here’s a non-exhaustive list of values expected for this
    *   attribute: `archive`, `compressed`, `directory`, `encrypted`, `execute`, `hidden`, `immutable`, `journaled`,
    *   `read`, `readonly`, `symbolic link`, `system`, `temporary`, `write`.
    */
  val FileAttributes: AttributeKey[Seq[String]] =
    AttributeKey("file.attributes")

  /** Time when the file attributes or metadata was last changed, in ISO 8601 format.
    *
    * @note
    *   <p> `file.changed` captures the time when any of the file's properties or attributes (including the content) are
    *   changed, while `file.modified` captures the timestamp when the file content is modified.
    */
  val FileChanged: AttributeKey[String] =
    AttributeKey("file.changed")

  /** Time when the file was created, in ISO 8601 format.
    *
    * @note
    *   <p> This attribute might not be supported by some file systems — NFS, FAT32, in embedded OS, etc.
    */
  val FileCreated: AttributeKey[String] =
    AttributeKey("file.created")

  /** Directory where the file is located. It should include the drive letter, when appropriate.
    */
  val FileDirectory: AttributeKey[String] =
    AttributeKey("file.directory")

  /** File extension, excluding the leading dot.
    *
    * @note
    *   <p> When the file name has multiple extensions (example.tar.gz), only the last one should be captured ("gz", not
    *   "tar.gz").
    */
  val FileExtension: AttributeKey[String] =
    AttributeKey("file.extension")

  /** Name of the fork. A fork is additional data associated with a filesystem object.
    *
    * @note
    *   <p> On Linux, a resource fork is used to store additional data with a filesystem object. A file always has at
    *   least one fork for the data portion, and additional forks may exist. On NTFS, this is analogous to an Alternate
    *   Data Stream (ADS), and the default data stream for a file is just called $$DATA. Zone.Identifier is commonly
    *   used by Windows to track contents downloaded from the Internet. An ADS is typically of the form:
    *   C:\path\to\filename.extension:some_fork_name, and some_fork_name is the value that should populate `fork_name`.
    *   `filename.extension` should populate `file.name`, and `extension` should populate `file.extension`. The full
    *   path, `file.path`, will include the fork name.
    */
  val FileForkName: AttributeKey[String] =
    AttributeKey("file.fork_name")

  /** Primary Group ID (GID) of the file.
    */
  val FileGroupId: AttributeKey[String] =
    AttributeKey("file.group.id")

  /** Primary group name of the file.
    */
  val FileGroupName: AttributeKey[String] =
    AttributeKey("file.group.name")

  /** Inode representing the file in the filesystem.
    */
  val FileInode: AttributeKey[String] =
    AttributeKey("file.inode")

  /** Mode of the file in octal representation.
    */
  val FileMode: AttributeKey[String] =
    AttributeKey("file.mode")

  /** Time when the file content was last modified, in ISO 8601 format.
    */
  val FileModified: AttributeKey[String] =
    AttributeKey("file.modified")

  /** Name of the file including the extension, without the directory.
    */
  val FileName: AttributeKey[String] =
    AttributeKey("file.name")

  /** The user ID (UID) or security identifier (SID) of the file owner.
    */
  val FileOwnerId: AttributeKey[String] =
    AttributeKey("file.owner.id")

  /** Username of the file owner.
    */
  val FileOwnerName: AttributeKey[String] =
    AttributeKey("file.owner.name")

  /** Full path to the file, including the file name. It should include the drive letter, when appropriate.
    */
  val FilePath: AttributeKey[String] =
    AttributeKey("file.path")

  /** File size in bytes.
    */
  val FileSize: AttributeKey[Long] =
    AttributeKey("file.size")

  /** Path to the target of a symbolic link.
    *
    * @note
    *   <p> This attribute is only applicable to symbolic links.
    */
  val FileSymbolicLinkTargetPath: AttributeKey[String] =
    AttributeKey("file.symbolic_link.target_path")

}
