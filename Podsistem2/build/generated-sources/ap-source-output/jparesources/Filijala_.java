package jparesources;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import jparesources.Isplata;
import jparesources.Mesto;
import jparesources.Uplata;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-02-02T23:20:17")
@StaticMetamodel(Filijala.class)
public class Filijala_ { 

    public static volatile ListAttribute<Filijala, Isplata> isplataList;
    public static volatile ListAttribute<Filijala, Uplata> uplataList;
    public static volatile SingularAttribute<Filijala, Mesto> idmes;
    public static volatile SingularAttribute<Filijala, Integer> idfil;
    public static volatile SingularAttribute<Filijala, String> naziv;
    public static volatile SingularAttribute<Filijala, String> adresa;

}