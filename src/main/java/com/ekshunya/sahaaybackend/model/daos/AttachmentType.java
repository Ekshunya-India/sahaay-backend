package com.ekshunya.sahaaybackend.model.daos;

public enum AttachmentType {
	MOV,MPEG4,MP4,AVI,WMV,MPEGPS,FLV, //VIDEO_FORMAT
	WAV,FLAC,AIFF,ALAC,OGG,MP2,MP3,AAC,AMR,WMA, //AUDIO_FORMAT
	PDF, DOC, DOCX, PPT, PPTX, XLS, XLSX, EPUB, ODT, ODP, ODS, TXT, RTF, //DOCUMENT_FORMAT
	TIF, TIFF, BMP, JPG, JPEG, PNG, //IMAGE_FORMATS
	HOSTED
}