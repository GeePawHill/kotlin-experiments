package org.geepawhill.experiments

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail

class HookedUpTest {
    @Test
    fun `is this thing on`() {
        fail("This isn't on.")
    }
}