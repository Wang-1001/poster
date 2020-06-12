package com.niit.poster.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.niit.poster.web.rest.TestUtil;

public class BillTypeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BillType.class);
        BillType billType1 = new BillType();
        billType1.setId(1L);
        BillType billType2 = new BillType();
        billType2.setId(billType1.getId());
        assertThat(billType1).isEqualTo(billType2);
        billType2.setId(2L);
        assertThat(billType1).isNotEqualTo(billType2);
        billType1.setId(null);
        assertThat(billType1).isNotEqualTo(billType2);
    }
}
