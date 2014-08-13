package de.tudarmstadt.ukp.dkpro.argumentation.classification;


import java.io.File;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;



public class Test2
{

    public static void main(String[] args)
        throws Exception
    {
        File input = new File("/home/ukp-hiwi/Documents/Jsoup/example.html");
        Document doc = Jsoup.parse(input, "UTF-8", "http://example.com/");
        Element body = doc.getElementById("href");
        System.out.println(body.html());
        Elements links = body.getElementsByTag("p");
        for (Element link : links) {
          String linkHref = link.attr("href");
          String linkText = link.text();

          System.out.println(link);
        }

//        Elements links = doc.select("div[id~=ar.+]");




//        for (Element link : links) {
////          String linkHref = link.attr("ar.+");
////          String linkText = link.text();
////            System.out.println(link);
////          System.out.println(linkHref);
////          System.out.println(linkHref);
//            Elements names = link.select("a[href~=//www.createdebate.com/user/viewprofile/][title]");
////            Elements paragraphes = link.select("p");
//
//            for (Element name : names){
//
//                System.out.println(name.text());
//
//                Elements paragraphes = name.select("p");
//
//                for (Element paragraphe : paragraphes){
//
//                    System.out.println(paragraphe.text());
//
//                }
//
//            }



        }
    }
