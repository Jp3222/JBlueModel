/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jsoftware.com.jblue.model.trash;

import java.util.Map;
import jsoftware.com.jblue.model.dto.AuditableObjectMap;

/**
 *
 * @author juanp
 */
public class _PaymentConceptDTO extends AuditableObjectMap {

    private static final long serialVersionUID = 1L;

    public _PaymentConceptDTO(Map<String, Object> map) {
        super(map);
    }

    public _PaymentConceptDTO() {
        super(7);
    }

    public String getDescription() {
        return get("description").toString();
    }

    public String getDescriptionLong() {
        return get("description_long").toString();
    }

}
