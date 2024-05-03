package br.com.todeschini.persistence.report.fatherreport;

import br.com.todeschini.domain.business.report.fatherreport.spi.FatherReportMethods;
import br.com.todeschini.domain.exceptions.ResourceNotFoundException;
import br.com.todeschini.persistence.entities.aluminium.AluminiumSon;
import br.com.todeschini.persistence.entities.aluminium.UsedMolding;
import br.com.todeschini.persistence.entities.aluminium.UsedScrew;
import br.com.todeschini.persistence.entities.aluminium.UsedTrySquare;
import br.com.todeschini.persistence.entities.guides.Guide;
import br.com.todeschini.persistence.entities.guides.GuideMachine;
import br.com.todeschini.persistence.entities.mdf.*;
import br.com.todeschini.persistence.entities.mdp.MDPSon;
import br.com.todeschini.persistence.entities.mdp.UsedEdgeBanding;
import br.com.todeschini.persistence.entities.mdp.UsedGlue;
import br.com.todeschini.persistence.entities.mdp.UsedSheet;
import br.com.todeschini.persistence.entities.packaging.*;
import br.com.todeschini.persistence.entities.publico.Attachment;
import br.com.todeschini.persistence.entities.publico.Father;
import br.com.todeschini.persistence.entities.publico.Son;
import br.com.todeschini.persistence.publico.father.FatherRepository;
import com.itextpdf.io.font.FontConstants;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Set;

@Component
public class FatherReportMethodsImpl implements FatherReportMethods {

    private final FatherRepository fatherRepository;

    public FatherReportMethodsImpl(FatherRepository fatherRepository) {
        this.fatherRepository = fatherRepository;
    }

    @Override
    public ByteArrayOutputStream generatePdfReport(Long id) throws IOException {
        Father father = fatherRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Pai não encontrado: " + id));

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        // Inicializa um novo documento PDF
        PdfWriter writer = new PdfWriter(outputStream);
        PdfDocument pdf = new PdfDocument(writer);

        // Cria um novo documento iTextPDF
        Document document = new Document(pdf);

        float pageSize = pdf.getDefaultPageSize().getWidth() - document.getLeftMargin() - document.getRightMargin();
        pdf.addEventHandler(PdfDocumentEvent.END_PAGE, new MyEventHandler());

        // Adiciona nome e logo da empresa no topo
        Image logo = new Image(ImageDataFactory.create("persistence/src/main/resources/todeschini-heart.png"));
        logo.setWidth(50);
        document.add(logo);

        // Adiciona uma linha sólida para separar o topo do conteúdo
        document.add(new LineSeparator(new SolidLine()));

        // Adiciona conteúdo ao documento
        PdfFont font = PdfFontFactory.createFont(FontConstants.HELVETICA);
        PdfFont boldFont = PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD);

        // Título do relatório
        Paragraph title = new Paragraph(father.getCode() + " - " + father.getDescription()).setFont(boldFont);
        document.add(title);

        document.add(new Paragraph("R$ " + father.getValue())).setFont(font);

        // Adiciona outra linha sólida para separar o título do restante do conteúdo
        document.add(new LineSeparator(new SolidLine()));

        // Subtítulo
        Paragraph subtitle = new Paragraph("FILHOS").setFont(boldFont);
        document.add(subtitle);

        // Lista de filhos
        for(Son son : father.getSons()){
            if(son instanceof MDPSon){
                generateMDPSonReport((MDPSon) son, document, font, boldFont, pageSize);
                document.add(new Paragraph(""));
            } else if (son instanceof PaintingSon) {
                generatePaintingSonReport((PaintingSon) son, document, font, boldFont, pageSize);
                document.add(new Paragraph(""));
            } else if (son instanceof AluminiumSon) {
                generateAluminiumSonReport((AluminiumSon) son, document, font ,boldFont, pageSize);
                document.add(new Paragraph(""));
            }
        }

        if(!father.getAttachments().isEmpty()){
            subtitle = new Paragraph("ACESSÓRIOS").setFont(boldFont);
            document.add(subtitle);
            generateAttachmentReport(father.getAttachments(), document, font, boldFont, pageSize);
            document.add(new Paragraph(""));
        }

        if(father.getGhost() != null){
            generateGhostReport(father.getGhost(), document, font, boldFont, pageSize);
            document.add(new Paragraph(""));
        }

        // Fecha o documento
        document.close();

        return outputStream;
    }

    private void generateMDPSonReport(MDPSon son, Document document, PdfFont font, PdfFont boldFont, float pageSize){
        document.add(new Paragraph(son.getCode() + " - " + son.getDescription()).setFont(boldFont));

        // Cria uma tabela com 5 colunas
        Table table = new Table(5);

        // Adiciona cabeçalhos das colunas
        table.addCell(new Cell().add(new Paragraph("Descrição").setFont(boldFont)));
        table.addCell(new Cell().add(new Paragraph("Código").setFont(boldFont)));
        table.addCell(new Cell().add(new Paragraph("Qt liq").setFont(boldFont)));
        table.addCell(new Cell().add(new Paragraph("Qt bt").setFont(boldFont)));
        table.addCell(new Cell().add(new Paragraph("R$").setFont(boldFont)));

        // Adiciona os itens da estrutura à tabela
        for (UsedSheet usedSheet : son.getSheets()) {
            table.addCell(new Cell().add(new Paragraph(usedSheet.getSheet().getDescription()).setFont(font)));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(usedSheet.getSheet().getCode())).setFont(font)));
            table.addCell(new Cell().add(new Paragraph(usedSheet.getNetQuantity() + " M²").setFont(font)));
            table.addCell(new Cell().add(new Paragraph(usedSheet.getGrossQuantity() + " M²").setFont(font)));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(usedSheet.calculateValue()))).setFont(font)); // Adicione suas observações aqui
        }

        for (UsedEdgeBanding usedEdgeBanding : son.getEdgeBandings()) {
            table.addCell(new Cell().add(new Paragraph(usedEdgeBanding.getEdgeBanding().getDescription()).setFont(font)));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(usedEdgeBanding.getEdgeBanding().getCode())).setFont(font)));
            table.addCell(new Cell().add(new Paragraph(usedEdgeBanding.getNetQuantity() + " M").setFont(font)));
            table.addCell(new Cell().add(new Paragraph(usedEdgeBanding.getGrossQuantity() + " M").setFont(font)));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(usedEdgeBanding.calculateValue()))).setFont(font));
        }

        for (UsedGlue usedGlue : son.getGlues()) {
            table.addCell(new Cell().add(new Paragraph(usedGlue.getGlue().getDescription()).setFont(font)));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(usedGlue.getGlue().getCode())).setFont(font)));
            table.addCell(new Cell().add(new Paragraph(usedGlue.getNetQuantity() + " kg").setFont(font)));
            table.addCell(new Cell().add(new Paragraph(usedGlue.getGrossQuantity() + " kg").setFont(font)));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(usedGlue.calculateValue()))).setFont(font));
        }

        table.setWidth(pageSize);
        // Adiciona a tabela ao documento
        document.add(table);

        document.add(new Paragraph(""));
        document.add(new Paragraph(""));

        generateGuideReport(son.getGuide(), document, font, boldFont, pageSize);
    }
    
    private void generatePaintingSonReport(PaintingSon son, Document document, PdfFont font, PdfFont boldFont, float pageSize){
        document.add(new Paragraph(son.getCode() + " - " + son.getDescription()).setFont(boldFont));

        // Cria uma tabela com 5 colunas
        Table table = new Table(5);

        // Adiciona cabeçalhos das colunas
        table.addCell(new Cell().add(new Paragraph("Descrição").setFont(boldFont)));
        table.addCell(new Cell().add(new Paragraph("Código").setFont(boldFont)));
        table.addCell(new Cell().add(new Paragraph("Qt liq").setFont(boldFont)));
        table.addCell(new Cell().add(new Paragraph("Qt bt").setFont(boldFont)));
        table.addCell(new Cell().add(new Paragraph("R$").setFont(boldFont)));

        // Adiciona os itens da estrutura à tabela
        for (UsedBackSheet usedBackSheet : son.getBack().getSheets()) {
            table.addCell(new Cell().add(new Paragraph(usedBackSheet.getSheet().getDescription()).setFont(font)));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(usedBackSheet.getSheet().getCode())).setFont(font)));
            table.addCell(new Cell().add(new Paragraph(usedBackSheet.getNetQuantity() + " M²").setFont(font)));
            table.addCell(new Cell().add(new Paragraph(usedBackSheet.getGrossQuantity() + " M²").setFont(font)));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(usedBackSheet.calculateValue()))).setFont(font)); // Adicione suas observações aqui
        }

        for (UsedPainting usedPainting : son.getPaintings()) {
            table.addCell(new Cell().add(new Paragraph(usedPainting.getPainting().getDescription()).setFont(font)));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(usedPainting.getPainting().getCode())).setFont(font)));
            table.addCell(new Cell().add(new Paragraph(usedPainting.getNetQuantity() + " M²").setFont(font)));
            table.addCell(new Cell().add(new Paragraph(usedPainting.getGrossQuantity() + " M²").setFont(font)));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(usedPainting.calculateValue()))).setFont(font));
        }

        for (UsedPaintingBorderBackground usedPaintingBorderBackground : son.getPaintingBorderBackgrounds()) {
            table.addCell(new Cell().add(new Paragraph(usedPaintingBorderBackground.getPaintingBorderBackground().getDescription()).setFont(font)));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(usedPaintingBorderBackground.getPaintingBorderBackground().getCode())).setFont(font)));
            table.addCell(new Cell().add(new Paragraph(usedPaintingBorderBackground.getNetQuantity() + " M").setFont(font)));
            table.addCell(new Cell().add(new Paragraph(usedPaintingBorderBackground.getGrossQuantity() + " M").setFont(font)));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(usedPaintingBorderBackground.calculateValue()))).setFont(font));
        }

        for (UsedPolyester usedPolyester : son.getPolyesters()) {
            table.addCell(new Cell().add(new Paragraph(usedPolyester.getPolyester().getDescription()).setFont(font)));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(usedPolyester.getPolyester().getCode())).setFont(font)));
            table.addCell(new Cell().add(new Paragraph(usedPolyester.getNetQuantity() + " M²").setFont(font)));
            table.addCell(new Cell().add(new Paragraph(usedPolyester.getGrossQuantity() + " M²").setFont(font)));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(usedPolyester.calculateValue()))).setFont(font));
        }

        table.setWidth(pageSize);
        // Adiciona a tabela ao documento
        document.add(table);

        document.add(new Paragraph(""));
        document.add(new Paragraph(""));

        generateGuideReport(son.getGuide(), document, font, boldFont, pageSize);
    }

    private void generateAluminiumSonReport(AluminiumSon aluminiumSon, Document document, PdfFont font, PdfFont boldFont, float pageSize) {
        document.add(new Paragraph(aluminiumSon.getCode() + " - " + aluminiumSon.getDescription()).setFont(boldFont));

        // Cria uma tabela com 4 colunas
        Table table = new Table(4);

        // Adiciona cabeçalhos das colunas
        table.addCell(new Cell().add(new Paragraph("Descrição").setFont(boldFont)));
        table.addCell(new Cell().add(new Paragraph("Código").setFont(boldFont)));
        table.addCell(new Cell().add(new Paragraph("Qt").setFont(boldFont)));
        table.addCell(new Cell().add(new Paragraph("R$").setFont(boldFont)));

        if(aluminiumSon.getDrawerPull() != null){
            table.addCell(new Cell().add(new Paragraph(aluminiumSon.getDrawerPull().getDrawerPull().getDescription()).setFont(font)));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(aluminiumSon.getDrawerPull().getDrawerPull().getCode())).setFont(font)));
            table.addCell(new Cell().add(new Paragraph(aluminiumSon.getDrawerPull().getQuantity() + " UN").setFont(font)));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(aluminiumSon.getDrawerPull().calculateValue()))).setFont(font));
        }

        if(aluminiumSon.getGlass() != null){
            table.addCell(new Cell().add(new Paragraph(aluminiumSon.getGlass().getGlass().getDescription()).setFont(font)));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(aluminiumSon.getGlass().getGlass().getCode())).setFont(font)));
            table.addCell(new Cell().add(new Paragraph(aluminiumSon.getGlass().getQuantity() + " UN").setFont(font)));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(aluminiumSon.getGlass().calculateValue()))).setFont(font));
        }

        for(UsedMolding molding : aluminiumSon.getMoldings()){
            table.addCell(new Cell().add(new Paragraph(molding.getMolding().getDescription()).setFont(font)));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(molding.getMolding().getCode())).setFont(font)));
            table.addCell(new Cell().add(new Paragraph(molding.getQuantity() + " M").setFont(font)));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(molding.calculateValue()))).setFont(font));
        }

        for(UsedScrew screw : aluminiumSon.getScrews()){
            table.addCell(new Cell().add(new Paragraph(screw.getScrew().getDescription()).setFont(font)));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(screw.getScrew().getCode())).setFont(font)));
            table.addCell(new Cell().add(new Paragraph(screw.getQuantity() + " UN").setFont(font)));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(screw.calculateValue()))).setFont(font));
        }

        for(UsedTrySquare trySquare : aluminiumSon.getTrySquares()){
            table.addCell(new Cell().add(new Paragraph(trySquare.getTrySquare().getDescription()).setFont(font)));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(trySquare.getTrySquare().getCode())).setFont(font)));
            table.addCell(new Cell().add(new Paragraph(trySquare.getQuantity() + " UN").setFont(font)));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(trySquare.calculateValue()))).setFont(font));
        }

        table.setWidth(pageSize);
        // Adiciona a tabela ao documento
        document.add(table);

        document.add(new Paragraph(""));
        document.add(new Paragraph("FILHOS MDP").setFont(boldFont));
        document.add(new Paragraph(""));

        for(MDPSon mdpSon : aluminiumSon.getSons()){
            generateMDPSonReport(mdpSon, document, font, boldFont, pageSize);
        }
    }

    private void generateAttachmentReport(Set<Attachment> attachments, Document document, PdfFont font , PdfFont boldFont, float pageSize){
        // Cria uma tabela com 4 colunas
        Table table = new Table(3);

        // Adiciona cabeçalhos das colunas
        table.addCell(new Cell().add(new Paragraph("Descrição").setFont(boldFont)));
        table.addCell(new Cell().add(new Paragraph("Código").setFont(boldFont)));
        table.addCell(new Cell().add(new Paragraph("R$").setFont(boldFont)));

        for(Attachment attachment : attachments){
            table.addCell(new Cell().add(new Paragraph(attachment.getDescription()).setFont(font)));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(attachment.getCode())).setFont(font)));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(attachment.getValue()))).setFont(font));
        }

        table.setWidth(pageSize);
        document.add(table);
    }

    private void generateGhostReport(Ghost ghost, Document document, PdfFont font, PdfFont boldFont, float pageSize){
        document.add(new Paragraph("EMBALAGEM").setFont(boldFont));
        document.add(new Paragraph(ghost.getCode() + " - " + ghost.getDescription()).setFont(boldFont));

        Table table = new Table(5);
        table.addCell(new Cell().add(new Paragraph("Descrição").setFont(boldFont)));
        table.addCell(new Cell().add(new Paragraph("Código").setFont(boldFont)));
        table.addCell(new Cell().add(new Paragraph("Qt liq").setFont(boldFont)));
        table.addCell(new Cell().add(new Paragraph("Qt bt").setFont(boldFont)));
        table.addCell(new Cell().add(new Paragraph("R$").setFont(boldFont)));

        for (UsedCornerBracket usedCornerBracket : ghost.getCornerBrackets()) {
            table.addCell(new Cell().add(new Paragraph(usedCornerBracket.getCornerBracket().getDescription()).setFont(font)));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(usedCornerBracket.getCornerBracket().getCode())).setFont(font)));
            table.addCell(new Cell().add(new Paragraph(usedCornerBracket.getNetQuantity() + " UN").setFont(font)));
            table.addCell(new Cell().add(new Paragraph(usedCornerBracket.getGrossQuantity() + " UN").setFont(font)));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(usedCornerBracket.calculateValue()))).setFont(font));
        }

        for (UsedNonwovenFabric usedNonwovenFabric : ghost.getNonwovenFabrics()) {
            table.addCell(new Cell().add(new Paragraph(usedNonwovenFabric.getNonwovenFabric().getDescription()).setFont(font)));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(usedNonwovenFabric.getNonwovenFabric().getCode())).setFont(font)));
            table.addCell(new Cell().add(new Paragraph(usedNonwovenFabric.getNetQuantity() + " M²").setFont(font)));
            table.addCell(new Cell().add(new Paragraph(usedNonwovenFabric.getGrossQuantity() + " M²").setFont(font)));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(usedNonwovenFabric.calculateValue()))).setFont(font));
        }

        for (UsedPlastic usedPlastic : ghost.getPlastics()) {
            table.addCell(new Cell().add(new Paragraph(usedPlastic.getPlastic().getDescription()).setFont(font)));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(usedPlastic.getPlastic().getCode())).setFont(font)));
            table.addCell(new Cell().add(new Paragraph(usedPlastic.getNetQuantity() + " M²").setFont(font)));
            table.addCell(new Cell().add(new Paragraph(usedPlastic.getGrossQuantity() + " M²").setFont(font)));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(usedPlastic.calculateValue()))).setFont(font));
        }

        for (UsedPolyethylene usedPolyethylene : ghost.getPolyethylenes()) {
            table.addCell(new Cell().add(new Paragraph(usedPolyethylene.getPolyethylene().getDescription()).setFont(font)));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(usedPolyethylene.getPolyethylene().getCode())).setFont(font)));
            table.addCell(new Cell().add(new Paragraph(usedPolyethylene.getNetQuantity() + " M²").setFont(font)));
            table.addCell(new Cell().add(new Paragraph(usedPolyethylene.getGrossQuantity() + " M²").setFont(font)));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(usedPolyethylene.calculateValue()))).setFont(font));
        }

        table.setWidth(pageSize);
        document.add(table);
    }

    private void generateGuideReport(Guide guide, Document document, PdfFont font, PdfFont boldFont, float pageSize){
        document.add(new Paragraph("ROTEIRO").setFont(boldFont));
        document.add(new Paragraph("Implementação: " + guide.getImplementation() + ". Data limite: " + guide.getFinalDate() + ".").setFont(font));

        Table table = new Table(4);

        // Adiciona cabeçalhos das colunas
        table.addCell(new Cell().add(new Paragraph("Máquina").setFont(boldFont)));
        table.addCell(new Cell().add(new Paragraph("Tempo Homem [MIN]").setFont(boldFont)));
        table.addCell(new Cell().add(new Paragraph("Tempo Máquina [MIN]").setFont(boldFont)));
        table.addCell(new Cell().add(new Paragraph("R$").setFont(boldFont)));

        for(GuideMachine guideMachine : guide.getGuideMachines()){
            table.addCell(new Cell().add(new Paragraph(guideMachine.getMachine().getName()).setFont(font)));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(guideMachine.getManTime())).setFont(font)));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(guideMachine.getMachineTime())).setFont(font)));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(guideMachine.calculateMachineValue() + guideMachine.calculateManValue()))).setFont(font));
        }

        table.setWidth(pageSize);
        document.add(table);
        document.add(new Paragraph(""));
    }
}

class MyEventHandler implements IEventHandler {
    @Override
    public void handleEvent(Event event) {
        PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
        PdfPage page = docEvent.getPage();
        PdfCanvas canvas = new PdfCanvas(page.newContentStreamBefore(), page.getResources(), page.getDocument());

        // Adiciona o número da página no canto direito inferior
        Rectangle pageSize = page.getPageSize();
        try {
            canvas.beginText()
                    .setFontAndSize(PdfFontFactory.createFont(FontConstants.HELVETICA), 9)
                    .moveText(pageSize.getRight() - 50, pageSize.getBottom() + 15)
                    .showText("Página " + docEvent.getDocument().getPageNumber(page))
                    .endText();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Adiciona um line separator na extremidade inferior da página
        canvas.moveTo(pageSize.getLeft(), pageSize.getBottom() + 30)
                .lineTo(pageSize.getRight(), pageSize.getBottom() + 30)
                .stroke();

        canvas.release();
    }
}


