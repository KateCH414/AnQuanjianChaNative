package com.miko.zd.anquanjianchanative.Utils;
/*
 * Created by zd on 2016/11/5.
 */

import android.content.Intent;
import android.net.Uri;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;

import java.io.File;
import java.io.IOException;

public class PdfUtils {


    //android获取一个用于打开PDF文件的intent

    public static Intent getPdfFileIntent(String param)

    {

        Intent intent = new Intent("android.intent.action.VIEW");

        intent.addCategory("android.intent.category.DEFAULT");

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        Uri uri = Uri.fromFile(new File(param));

        intent.setDataAndType(uri, "application/pdf");

        return intent;
    }

    public static Chunk getChunk(int textSize, String text) throws IOException, DocumentException {
        BaseFont bfHei;
        bfHei = BaseFont.createFont("assets/fonts/simhei.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        return new Chunk(text, new Font(bfHei, textSize));
    }

    public static Paragraph getParagraph(int textSize, String text) throws IOException, DocumentException {
        BaseFont bfHei = BaseFont.createFont("assets/fonts/simhei.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        return new Paragraph(text, new Font(bfHei, textSize));
    }

    public static Paragraph getBParagraph(int textSize, String text) throws IOException, DocumentException {
        BaseFont bfHei = BaseFont.createFont("assets/fonts/simhei.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        return new Paragraph(text, new Font(bfHei, textSize,Font.BOLD));
    }

}
