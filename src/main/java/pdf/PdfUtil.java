package pdf;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class PdfUtil {
	
	private static BaseFont baseFont = getBaseFont();
	private static Font chineseFont = new Font(baseFont);

		
	public static void exportPdf(File file) throws Exception{
		
		// 创建Document对象, 默认PageSize为A4, 各边距为36
//		Document document = new Document();
		// 指定PageSize, 各边距为36
//		Document document = new Document(PageSize.A4);
		// 指定PageSize和边距(左、右、上、下)
		Document document = new Document(PageSize.A4, 50, 50, 50, 50);
		
		
		// 创建PdfWriter, 类似还有HtmlWriter、RtfWriter、XmlWriter 
		PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(file));
		
		// 打开Document
		document.open();
		
		
		// 向文档添加内容
		
		// 段落 - 文本
		Paragraph paragraph1 = new Paragraph("Hello Itext!");
		Paragraph paragraph2 = new Paragraph("显示中文", chineseFont);
		Paragraph paragraph3 = new Paragraph("自定义字体样式", new Font(baseFont, 10f));
		
		// 段落 - 超链接
		Paragraph paragraph4 = new Paragraph();
		Anchor anchor = new Anchor("First page of the document");
		anchor.setName("BackToTop");
		paragraph4.add(anchor);
		paragraph4.setSpacingBefore(20);	// 间距 - 前
		paragraph4.setSpacingAfter(20);	// 间距 - 后
		
		// 章
		Chapter chapter1 = new Chapter(1);
		Chapter chapter2 = new Chapter("chapter2", 2);
		Chapter chapter3 = new Chapter(new Paragraph("chapter3"), 3);
		chapter3.setNumberDepth(0);	// 隐藏序号
		
		// 节
		Section section1 = chapter1.addSection("section1");
		Section section2 = chapter1.addSection("section2");
		Section section3 = chapter1.addSection("section3");
		Section section4 = chapter1.addSection(new Paragraph("section4"));
		Section section5 = chapter1.addSection(new Paragraph("section5"));
		section1.add(paragraph1);
		section1.add(paragraph2);
		section1.add(paragraph3);
		section2.add(paragraph4);
		
		// 表
		PdfPTable table = new PdfPTable(3);	// 指定列数
		table.setSpacingBefore(20);	// 间距
		table.setSpacingAfter(20);
	    PdfPCell c1 = new PdfPCell(new Phrase("Cell1"));  // 单元格
	    PdfPCell c2 = new PdfPCell(new Phrase("Cell2"));
	    PdfPCell c3 = new PdfPCell(new Phrase("Cell3"));
	    table.addCell(c1);
	    table.addCell(c2);
	    table.addCell(c3);
	    table.addCell("1.1");
	    table.addCell("1.2");
	    table.addCell("1.3");
	    section3.add(table);
	    
	    // 列表
	    List list1 = new List();
	    ListItem listItem1 = new ListItem("ListItem1");
	    list1.add(listItem1);
	    list1.add("ListItem2");
	    section4.add(list1);
	    
	    // 图片
	    Image image1 = Image.getInstance(ClassLoader.getSystemResource("pic.jpg"));
	    Image image2 = Image.getInstance(ClassLoader.getSystemResource("pic.jpg"));
		image1.scaleAbsolute(120f, 80f);
		section5.add(image1);
		section5.add(image2);
		
		document.add(chapter1);
		document.add(chapter2);
		document.add(chapter3);
		
		
		// 添加水印
		Image image = Image.getInstance(ClassLoader.getSystemResource("mark.png"));
		image.scaleAbsolute(260f, 160f);	// 大小
		image.setAbsolutePosition(180f, 280f); // 坐标
		PdfContentByte contentByte = pdfWriter.getDirectContent();
		contentByte.addImage(image);
		
		
		// 关闭Document
		document.close();
		
	}
	
	
	/**
	 * 获取中文字体
	 * @return
	 */
	private static BaseFont getBaseFont(){
        try {
			return BaseFont.createFont("simfang.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
    }
	
}
