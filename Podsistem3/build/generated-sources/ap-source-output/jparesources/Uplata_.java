package jparesources;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import jparesources.Filijala;
import jparesources.Racun;
import jparesources.Transakcija;
import jparesources.UplataPK;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-02-02T23:16:57")
@StaticMetamodel(Uplata.class)
public class Uplata_ { 

    public static volatile SingularAttribute<Uplata, UplataPK> uplataPK;
    public static volatile SingularAttribute<Uplata, Transakcija> transakcija;
    public static volatile SingularAttribute<Uplata, Filijala> idfil;
    public static volatile SingularAttribute<Uplata, Racun> idracna;

}