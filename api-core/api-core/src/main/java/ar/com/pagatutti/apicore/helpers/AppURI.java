package ar.com.pagatutti.apicore.helpers;

public abstract class AppURI {

    public static final String ID = "/{id}";

    public static final String ENT_BASE = "/empresas";
    public static final String ENT_GET = ENT_BASE + ID;

    public static final String ENT_ACC_STATUS_BASE = "/estado-contable";
    public static final String ENT_ACC_STATUS_GET = ENT_ACC_STATUS_BASE + ID;

    public static final String ENT_IMMOVABLE_BASE = "/inmueble";
    public static final String ENT_IMMOVABLE_GET = ENT_IMMOVABLE_BASE + ID;

    public static final String ENT_VEHICLE_BASE = "/rodado";
    public static final String ENT_VEHICLE_GET = ENT_VEHICLE_BASE + ID;

    public static final String ENT_POST_SALES_BASE = "/post-ventas";
    public static final String ENT_POST_SALES_GET = ENT_POST_SALES_BASE + ID;

    public static final String ENT_DEBT_BASE = "/endeudamiento";
    public static final String ENT_DEBT_GET = ENT_DEBT_BASE + ID;

    public static final String ENT_OPPORTUNITY_BASE = "/oportunidad-empresa";
    public static final String ENT_OPPORTUNITY_GET = ENT_OPPORTUNITY_BASE + ID;

    public static final String IND_OPPORTUNITY_BASE = "/oportunidad-individuo";
    public static final String IND_OPPORTUNITY_GET = IND_OPPORTUNITY_BASE + ID;

    public static final String IND_BASE = "/individuo";
    public static final String IND_GET = IND_BASE + ID;

    public static final String IND_LOAN_BASE = IND_BASE + "/prestamos";
    public static final String IND_LOAN_GET = IND_LOAN_BASE + ID;

    public static final String IND_CREDIT_LINE_BASE = IND_BASE + "/linea-credito";
    public static final String IND_CREDIT_LINE_GET = IND_CREDIT_LINE_BASE + ID;

    public static final String IND_CREDIT_CARD_BASE = IND_BASE + "/tdc";
    public static final String IND_CREDIT_CARD_GET = IND_CREDIT_CARD_BASE + ID;

    public static final String IND_VIRTUAL_WALLET_BASE = IND_BASE + "/billetera-virtual";
    public static final String IND_VIRTUAL_WALLET_GET = IND_VIRTUAL_WALLET_BASE + ID;

    public static final String IND_OP_REQ_PRODUCTS = IND_OPPORTUNITY_GET  + "/productos-solicitados";
    public static final String IND_OP_CURR_PRODUCTS = IND_OPPORTUNITY_GET  + "/productos-vigentes";

}