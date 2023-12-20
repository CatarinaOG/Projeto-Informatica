package com.bosch.diabo.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.bosch.diabo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FlaggedMaterialTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FlaggedMaterial.class);
        FlaggedMaterial flaggedMaterial1 = new FlaggedMaterial();
        flaggedMaterial1.setId(1L);
        FlaggedMaterial flaggedMaterial2 = new FlaggedMaterial();
        flaggedMaterial2.setId(flaggedMaterial1.getId());
        assertThat(flaggedMaterial1).isEqualTo(flaggedMaterial2);
        flaggedMaterial2.setId(2L);
        assertThat(flaggedMaterial1).isNotEqualTo(flaggedMaterial2);
        flaggedMaterial1.setId(null);
        assertThat(flaggedMaterial1).isNotEqualTo(flaggedMaterial2);
    }
}
