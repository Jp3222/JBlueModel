/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jsoftware.com.jblue.model.dto.vw;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 *
 * @author juanp
 */
public class WaterIntakeUserPaymentDTO {

    private int id_toma;
    private int id_usuario;
    private String numero_toma;
    private String nombre_usuario;
    private String tipo_toma;
    private String tipo_usuario;
    private BigDecimal cuota;
    private int ult_año_pago;
    private int ult_mes_pago;
    private int meses_pagados;
    private boolean ene;
    private boolean feb;
    private boolean mar;
    private boolean abr;
    private boolean may;
    private boolean jun;
    private boolean jul;
    private boolean ago;
    private boolean sep;
    private boolean oct;
    private boolean nov;
    private boolean dic;
    private LocalDateTime date_register;

    public int getId_toma() {
        return id_toma;
    }

    public void setId_toma(int id_toma) {
        this.id_toma = id_toma;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNumero_toma() {
        return numero_toma;
    }

    public void setNumero_toma(String numero_toma) {
        this.numero_toma = numero_toma;
    }

    public String getNombre_usuario() {
        return nombre_usuario;
    }

    public void setNombre_usuario(String nombre_usuario) {
        this.nombre_usuario = nombre_usuario;
    }

    public String getTipo_toma() {
        return tipo_toma;
    }

    public void setTipo_toma(String tipo_toma) {
        this.tipo_toma = tipo_toma;
    }

    public String getTipo_usuario() {
        return tipo_usuario;
    }

    public void setTipo_usuario(String tipo_usuario) {
        this.tipo_usuario = tipo_usuario;
    }

    public BigDecimal getCuota() {
        return cuota;
    }

    public void setCuota(BigDecimal cuota) {
        this.cuota = cuota;
    }

    public int getUlt_año_pago() {
        return ult_año_pago;
    }

    public void setUlt_año_pago(int ult_año_pago) {
        this.ult_año_pago = ult_año_pago;
    }

    public int getUlt_mes_pago() {
        return ult_mes_pago;
    }

    public void setUlt_mes_pago(int ult_mes_pago) {
        this.ult_mes_pago = ult_mes_pago;
    }

    public int getMeses_pagados() {
        return meses_pagados;
    }

    public void setMeses_pagados(int meses_pagados) {
        this.meses_pagados = meses_pagados;
    }

    public boolean isEne() {
        return ene;
    }

    public void setEne(boolean ene) {
        this.ene = ene;
    }

    public boolean isFeb() {
        return feb;
    }

    public void setFeb(boolean feb) {
        this.feb = feb;
    }

    public boolean isMar() {
        return mar;
    }

    public void setMar(boolean mar) {
        this.mar = mar;
    }

    public boolean isAbr() {
        return abr;
    }

    public void setAbr(boolean abr) {
        this.abr = abr;
    }

    public boolean isMay() {
        return may;
    }

    public void setMay(boolean may) {
        this.may = may;
    }

    public boolean isJun() {
        return jun;
    }

    public void setJun(boolean jun) {
        this.jun = jun;
    }

    public boolean isJul() {
        return jul;
    }

    public void setJul(boolean jul) {
        this.jul = jul;
    }

    public boolean isAgo() {
        return ago;
    }

    public void setAgo(boolean ago) {
        this.ago = ago;
    }

    public boolean isSep() {
        return sep;
    }

    public void setSep(boolean sep) {
        this.sep = sep;
    }

    public boolean isOct() {
        return oct;
    }

    public void setOct(boolean oct) {
        this.oct = oct;
    }

    public boolean isNov() {
        return nov;
    }

    public void setNov(boolean nov) {
        this.nov = nov;
    }

    public boolean isDic() {
        return dic;
    }

    public void setDic(boolean dic) {
        this.dic = dic;
    }

    public LocalDateTime getDate_register() {
        return date_register;
    }

    public void setDate_register(LocalDateTime date_register) {
        this.date_register = date_register;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + this.id_toma;
        hash = 79 * hash + this.id_usuario;
        hash = 79 * hash + this.ult_año_pago;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final WaterIntakeUserPaymentDTO other = (WaterIntakeUserPaymentDTO) obj;
        if (this.id_toma != other.id_toma) {
            return false;
        }
        if (this.id_usuario != other.id_usuario) {
            return false;
        }
        return this.ult_año_pago == other.ult_año_pago;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("WaterIntakeUserPayment{");
        sb.append("id_toma=").append(id_toma);
        sb.append(", id_usuario=").append(id_usuario);
        sb.append(", numero_toma=").append(numero_toma);
        sb.append(", nombre_usuario=").append(nombre_usuario);
        sb.append(", tipo_toma=").append(tipo_toma);
        sb.append(", tipo_usuario=").append(tipo_usuario);
        sb.append(", cuota=").append(cuota);
        sb.append(", ult_a\u00f1o_pago=").append(ult_año_pago);
        sb.append(", ult_mes_pago=").append(ult_mes_pago);
        sb.append(", ene=").append(ene);
        sb.append(", feb=").append(feb);
        sb.append(", mar=").append(mar);
        sb.append(", may=").append(may);
        sb.append(", jun=").append(jun);
        sb.append(", jul=").append(jul);
        sb.append(", ago=").append(ago);
        sb.append(", sep=").append(sep);
        sb.append(", oct=").append(oct);
        sb.append(", nov=").append(nov);
        sb.append(", dic=").append(dic);
        sb.append(", date_register=").append(date_register);
        sb.append('}');
        return sb.toString();
    }

}
