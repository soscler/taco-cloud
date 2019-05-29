# key points


- Think of the domain (Model, Object, etc)
### Validate the form with javax validation tools (Java Bean Validation: JSR-303)
To apply validation in Spring MVC, you need to
- Declare validation rules on the class that is to be validated: specifically, the
Taco class.
- Specify that validation should be performed in the controller methods that
require validation: specifically, the DesignTacoController’s processDesign()
method and OrderController’s processOrder() method.
- Modify the form views to display validation errors.

## 
- Make more test (test for the API, the logic of the object, UnitTest)