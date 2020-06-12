package com.niit.poster.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.niit.poster.web.rest.TestUtil;

public class BillInfoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BillInfo.class);
        BillInfo billInfo1 = new BillInfo();
        billInfo1.setId(1L);
        BillInfo billInfo2 = new BillInfo();
        billInfo2.setId(billInfo1.getId());
        assertThat(billInfo1).isEqualTo(billInfo2);
        billInfo2.setId(2L);
        assertThat(billInfo1).isNotEqualTo(billInfo2);
        billInfo1.setId(null);
        assertThat(billInfo1).isNotEqualTo(billInfo2);
    }
}
