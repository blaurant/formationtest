package com.lb.formationtest.domain;

import DDD.ValueObject;
import DDD.framework.Strings;

@ValueObject
public enum Quality {

    PET_BF_COLORE("POLYTÉRÉPHTALATE D'ÉTHYLÈNE BF Coloré", "FD1"),
    PET_BF_CLAIR("POLYTÉRÉPHTALATE D'ÉTHYLÈNE BF Clair", "FD5"),
    PET_BF_COLORE_ET_OPAQUE("POLYTÉRÉPHTALATE D'ÉTHYLÈNE BF Coloré & Opaque", "FD10"),
    PET_OPAQUE_BLANC("POLYTÉRÉPHTALATE D'ÉTHYLÈNE Opaque blanc", "FD11"),
    PET_PB_CLAIR("POLYTÉRÉPHTALATE D'ÉTHYLÈNE PB clair", "FD3"),
    PP_FD8("POLYPROPYLÈNE", "FD11"),
    PS("POLYSTYRÈNE", "FD4");

    Quality(String name, String code) {
        this.wording = Strings.requireMinLength(name, 2);
        this.code = Strings.requireMinLength(code, 3);
    }

    private String wording;
    private String code;

    public String wording() {
        return wording;
    }

    public String code() {
        return code;
    }

    @Override
    public String toString() {
        return wording;
    }
}


/*
PET BF Coloré
FD1
PET BF Clair
FD5
PET Opaque
FD2
PET PB clair
FD3
PS
FD4
PEHD PP
FD6
PEHD
FD7
PP
FD8
PET MC
FD9
Refus
FD0
PET BF Coloré & Opaque
FD10
PET Opaque blanc
FD11
 */