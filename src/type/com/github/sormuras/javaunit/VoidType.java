/*
 * Copyright (C) 2016 Christian Stein
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package com.github.sormuras.javaunit;

import java.util.Collections;
import java.util.List;

public class VoidType extends JavaType<VoidType> {

  @Override
  public Listing apply(Listing listing) {
    return listing.add("void");
  }

  @Override
  public List<JavaAnnotation> getAnnotations() {
    return Collections.emptyList();
  }
}