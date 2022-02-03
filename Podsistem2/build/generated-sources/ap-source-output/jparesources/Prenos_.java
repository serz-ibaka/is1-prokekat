package jparesources;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import jparesources.PrenosPK;
import jparesources.Racun;
import jparesources.Transakcija;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-02-02T23:20:17")
@StaticMetamodel(Prenos.class)
public class Prenos_ { 

    public static volatile SingularAttribute<Prenos, Racun> idracsa;
    public static volatile SingularAttribute<Prenos, Transakcija> transakcija;
    public static volatile SingularAttribute<Prenos, Racun> idracna;
    public static volatile SingularAttribute<Prenos, PrenosPK> prenosPK;

}