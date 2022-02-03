package jparesources;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import jparesources.Filijala;
import jparesources.Komitent;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-02-02T23:05:07")
@StaticMetamodel(Mesto.class)
public class Mesto_ { 

    public static volatile SingularAttribute<Mesto, Integer> idmes;
    public static volatile SingularAttribute<Mesto, String> naziv;
    public static volatile SingularAttribute<Mesto, Integer> postanskibroj;
    public static volatile ListAttribute<Mesto, Filijala> filijalaList;
    public static volatile ListAttribute<Mesto, Komitent> komitentList;

}