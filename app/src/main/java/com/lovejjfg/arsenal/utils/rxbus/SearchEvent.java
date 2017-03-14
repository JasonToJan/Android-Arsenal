/*
 *  Copyright (c) 2017.  Joe
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package com.lovejjfg.arsenal.utils.rxbus;

/**
 * Created by Joe on 2017/3/13.
 * Email lovejjfg@gmail.com
 */

public class SearchEvent {
    public final String key;
    public final String tagName;
    public final int type;

    public SearchEvent(String key, String tagName, int type) {
        this.key = key;
        this.tagName = tagName;
        this.type = type;
    }
}