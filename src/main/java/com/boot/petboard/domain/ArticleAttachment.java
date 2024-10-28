//package com.boot.petboard.domain;
//
//import com.boot.petboard.domain.constant.Category;
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.Setter;
//
//
//@Entity
//@Table
//@Getter
//@Setter
//public class ArticleAttachment {
//    @Id
//    @Column(name="article_attachment_id")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String oriFileName;
//
//    private String fileName;
//
//    private String fileUrl;
//
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name="article_id")
//    private Article article;
//
//
//    protected ArticleAttachment() {}
//
//    private ArticleAttachment(String oriFileName, String fileName, String fileUrl) {
//        this.oriFileName = oriFileName;
//        this.fileName = fileName;
//        this.fileUrl = fileUrl;
//
//    }
//
//    public static ArticleAttachment of(String oriFileName, String fileName, String fileUrl) {
//        return new ArticleAttachment(oriFileName, fileName, fileUrl);
//    }
//    public void updateItemImg(String oriFileName, String fileName, String fileUrl){
//        this.oriFileName = oriFileName;
//        this.fileName = fileName;
//        this.fileUrl = fileUrl;
//    }
//
//}
