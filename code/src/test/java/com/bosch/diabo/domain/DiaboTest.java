package com.bosch.diabo.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.bosch.diabo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DiaboTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Diabo.class);
        Diabo diabo1 = new Diabo();
        diabo1.setId(1L);
        Diabo diabo2 = new Diabo();
        diabo2.setId(diabo1.getId());
        assertThat(diabo1).isEqualTo(diabo2);
        diabo2.setId(2L);
        assertThat(diabo1).isNotEqualTo(diabo2);
        diabo1.setId(null);
        assertThat(diabo1).isNotEqualTo(diabo2);
    }
}
