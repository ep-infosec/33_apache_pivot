/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.pivot.wtk;

import org.apache.pivot.util.ListenerList;
import org.apache.pivot.util.Vote;
import org.apache.pivot.util.VoteResult;

/**
 * Card pane listener interface.
 */
public interface CardPaneListener {
    /**
     * Card pane listeners.
     */
    public static class Listeners extends ListenerList<CardPaneListener> implements
        CardPaneListener {
        @Override
        public Vote previewSelectedIndexChange(CardPane cardPane, int selectedIndex) {
            VoteResult result = new VoteResult();

            forEach(listener -> result.tally(listener.previewSelectedIndexChange(cardPane, selectedIndex)));

            return result.get();
        }

        @Override
        public void selectedIndexChangeVetoed(CardPane cardPane, Vote reason) {
            forEach(listener -> listener.selectedIndexChangeVetoed(cardPane, reason));
        }

        @Override
        public void selectedIndexChanged(CardPane cardPane, int previousSelectedIndex) {
            forEach(listener -> listener.selectedIndexChanged(cardPane, previousSelectedIndex));
        }
    }

    /**
     * Card pane listener adapter.
     * @deprecated Since 2.1 and Java 8 the interface itself has default implementations.
     */
    @Deprecated
    public static class Adapter implements CardPaneListener {
        @Override
        public Vote previewSelectedIndexChange(CardPane cardPane, int selectedIndex) {
            return Vote.APPROVE;
        }

        @Override
        public void selectedIndexChangeVetoed(CardPane cardPane, Vote reason) {
            // empty block
        }

        @Override
        public void selectedIndexChanged(CardPane cardPane, int previousSelectedIndex) {
            // empty block
        }
    }

    /**
     * Called to preview a selected index change.
     *
     * @param cardPane The source of the event.
     * @param selectedIndex The index that will be selected.
     * @return The consensus vote as to whether to allow this change.
     */
    default Vote previewSelectedIndexChange(CardPane cardPane, int selectedIndex) {
        return Vote.APPROVE;
    }

    /**
     * Called when a selected index change has been vetoed.
     *
     * @param cardPane The source of the event.
     * @param reason The reason the event was vetoed.
     */
    default void selectedIndexChangeVetoed(CardPane cardPane, Vote reason) {
    }

    /**
     * Called when a card pane's selected index has changed.
     *
     * @param cardPane The source of the event.
     * @param previousSelectedIndex If the selection changed directly, contains
     * the index that was previously selected. Otherwise, contains the current
     * selection.
     */
    default void selectedIndexChanged(CardPane cardPane, int previousSelectedIndex) {
    }
}