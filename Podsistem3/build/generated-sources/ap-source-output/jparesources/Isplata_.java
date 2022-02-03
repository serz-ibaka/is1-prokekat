package jparesources;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import jparesources.Filijala;
import jparesources.IsplataPK;
import jparesources.Racun;
import jparesources.Transakcija;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-02-02T23:16:57")
@StaticMetamodel(Isplata.class)
public class Isplata_ { 

    public static volatile SingularAttribute<Isplata, Racun> idracsa;
    public static volatile SingularAttribute<Isplata, Transakcija> transakcija;
    public static volatile SingularAttribute<Isplata, IsplataPK> isplataPK;
    public static volatile SingularAttribute<Isplata, Filijala> idfil;

}