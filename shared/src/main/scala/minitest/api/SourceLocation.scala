/*
 * Copyright (c) 2014-2017 by its authors. Some rights reserved.
 * See the project homepage at: https://github.com/monix/minitest
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

package minitest.api

import scala.quoted._
import scala.tasty._

final case class SourceLocation(
  fileName: Option[String],
  filePath: Option[String],
  line: Int
)

object SourceLocation {
  inline implicit def fromContext: SourceLocation = ~fromContextImpl

  def fromContextImpl(implicit tasty: Tasty): Expr[SourceLocation] = {
    import tasty._
    val pos = tasty.rootPosition
    val fileName = pos.sourceFile.getFileName.toString
    val path = pos.sourceFile.getParent.toString
    val line = pos.startLine + 1
    '(SourceLocation(Some(~fileName.toExpr), Some(~path.toExpr), ~line.toExpr))
  }
}
