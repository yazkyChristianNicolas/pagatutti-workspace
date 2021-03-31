package ar.com.pagatutti.apicorebeans.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum MasterTablesEnum {
    FinancialEntites("ad_mt_banco"),
	IndividualSegment("ad_mt_individual_segment");

    @Getter private String value;
}

