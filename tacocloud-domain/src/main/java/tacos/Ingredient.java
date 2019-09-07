package tacos;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@RequiredArgsConstructor
@NoArgsConstructor( access = AccessLevel.PRIVATE, force = true)
@Entity
public class Ingredient {

    @Id
    private final String id;
    private final  String name;
    private final Type type;

    /**
     * @deprecated 
     * @// TODO: 02-09-19 create a json file to store the ingredients
     */
    public static enum Type {
        WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
    }

}
