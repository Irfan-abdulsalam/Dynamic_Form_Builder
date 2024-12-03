package com.Myprojects.Dynamic_Form_Builder;


import com.Myprojects.Dynamic_Form_Builder.util.Base62;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Base62Test {

    @Test
    void testGenerateUniqueId() {
        String uniqueId1 = Base62.generateUniqueId();
        String uniqueId2 = Base62.generateUniqueId();

        assertNotNull(uniqueId1);
        assertNotNull(uniqueId2);
        assertNotEquals(uniqueId1, uniqueId2);
        assertTrue(uniqueId1.length() > 0);
        assertTrue(uniqueId2.length() > 0);
    }
}
