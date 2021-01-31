/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesliant.test;

/**
 *
 * @author Lewis
 */
import com.salesliant.util.BaseListUI;
import static net.sf.dynamicreports.report.builder.DynamicReports.*;
import net.sf.dynamicreports.report.builder.component.ComponentBuilder;
import net.sf.dynamicreports.report.builder.component.DimensionComponentBuilder;
import net.sf.dynamicreports.report.builder.component.HorizontalListBuilder;
import net.sf.dynamicreports.report.exception.DRException;

/**
 * @author Ricardo Mariaca (r.mariaca@dynamicreports.org)
 */
public class BarcodeReportUI extends BaseListUI {

    public BarcodeReportUI() {
        build();
    }

    private void build() {
        try {
            report()
                    .setTemplate(template().setBarcodeHeight(40))
                    .title(
                            DemoTemplates.createTitleComponent("Barcode"),
                            cmp.text("Barcode4j").setStyle(DemoTemplates.bold18CenteredStyle),
                            barcode4j(),
                            cmp.verticalGap(10),
                            cmp.text("ZXing").setStyle(DemoTemplates.bold18CenteredStyle),
                            barcode("QrCode", bcode.qrCode("12345678")),
                            cmp.verticalGap(10),
                            cmp.text("Barbecue").setStyle(DemoTemplates.bold18CenteredStyle),
                            barbecue())
                    .show();
        } catch (DRException e) {
            e.printStackTrace();
        }
    }

    private ComponentBuilder<?, ?> barcode4j() {
        HorizontalListBuilder list = cmp.horizontalFlowList();
        list.setGap(10);
        list.add(
                barcode("Codabar", bcode.codabar("12345678")),
                barcode("Code128", bcode.code128("12345678")),
                barcode("Ean128", bcode.ean128("12345678")),
                barcode("DataMatrix", bcode.dataMatrix("12345678")),
                barcode("Code39", bcode.code39("12345678")),
                barcode("Interleaved2Of5", bcode.interleaved2Of5("12345678")),
                barcode("Upca", bcode.upca("11000000000")),
                barcode("Upce", bcode.upce("1100000")),
                barcode("Ean13", bcode.ean13("110000000000")),
                barcode("Ean8", bcode.ean8("1100000")),
                barcode("UspsIntelligentMail", bcode.uspsIntelligentMail("34160265194042788110")),
                barcode("RoyalMailCustomer", bcode.royalMailCustomer("34160265194042788110")),
                barcode("Postnet", bcode.postnet("12345678")),
                barcode("Pdf417", bcode.pdf417("12345678")));
        return list;
    }

    private ComponentBuilder<?, ?> barbecue() {
        HorizontalListBuilder list = cmp.horizontalFlowList();
        list.setGap(10);
        list.add(
                barcode("2of7", bcode.barbecue_2of7("12345678")),
                barcode("3of9", bcode.barbecue_3of9("12345678")),
                barcode("Bookland", bcode.barbecue_bookland("1234567890")),
                barcode("Codabar", bcode.barbecue_codabar("12345678")),
                barcode("Code128", bcode.barbecue_code128("12345678")),
                barcode("Code128A", bcode.barbecue_code128A("12345678")),
                barcode("Code128B", bcode.barbecue_code128B("12345678")),
                barcode("Code128C", bcode.barbecue_code128C("12345678")),
                barcode("Code39", bcode.barbecue_code39("12345678")),
                barcode("Code39 Extended", bcode.barbecue_code39Extended("12345678")),
                barcode("Ean128", bcode.barbecue_ean128("12345678")),
                barcode("Ean13", bcode.barbecue_ean13("123456789012")),
                barcode("GlobalTradeItemNumber", bcode.barbecue_globalTradeItemNumber("12345678")),
                barcode("Int2of5", bcode.barbecue_int2of5("12345678")),
                barcode("Monarch", bcode.barbecue_monarch("12345678")),
                barcode("Nw7", bcode.barbecue_nw7("12345678")),
                barcode("Pdf417", bcode.barbecue_pdf417("12345678")),
                barcode("Postnet", bcode.barbecue_postnet("12345678")),
                barcode("RandomWeightUpca", bcode.barbecue_randomWeightUpca("12345678901")),
                barcode("Scc14ShippingCode", bcode.barbecue_scc14ShippingCode("12345678")),
                barcode("ShipmentIdentificationNumber", bcode.barbecue_shipmentIdentificationNumber("12345678")),
                barcode("Sscc18", bcode.barbecue_sscc18("12345678")),
                barcode("Std2of5", bcode.barbecue_std2of5("12345678")),
                barcode("Ucc128", bcode.barbecue_ucc128("12345678").setApplicationIdentifierExpression("123")),
                barcode("Upca", bcode.barbecue_upca("12345678901")),
                barcode("Usd3", bcode.barbecue_usd3("12345678")),
                barcode("Usd4", bcode.barbecue_usd4("12345678")),
                barcode("Usps", bcode.barbecue_usps("12345678")));
        return list;
    }

    private ComponentBuilder<?, ?> barcode(String label, DimensionComponentBuilder<?, ?> barcode) {
        return cmp.verticalList(barcode, cmp.text(label).setStyle(DemoTemplates.bold12LeftStyle));
    }

    public static void main(String[] args) {
        new BarcodeReportUI();
    }

    @Override
    public void handleAction(String code) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
