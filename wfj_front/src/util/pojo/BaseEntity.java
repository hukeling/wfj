package util.pojo;
import java.io.Serializable;
public class BaseEntity implements Serializable {
	private static final long serialVersionUID = -6718838800112233445L;
	/* private String id;// ID
     @Id
     @GeneratedValue(generator = "uuid")
     public String getId() {
         return id;
     }
    public void setId(String id) {
         this.id = id;
     }
    @Override
     public int hashCode() {
         return id == null ? System.identityHashCode(this) : id.hashCode();
     }
    @Override
     public boolean equals(Object obj) {
         if (this == obj) {
             return true;
         }
         if (obj == null) {
             return false;
         }
         if (getClass().getPackage() != obj.getClass().getPackage()) {
             return false;
         }
         final BaseEntity other = (BaseEntity) obj;
         if (id == null) {
             if (other.getId() != null) {
                 return false;
             }
         } else if (!id.equals(other.getId())) {
             return false;
         }
         return true;
     }*/
}
