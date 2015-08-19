package com.entelgy.mediapro.spaininaday.sql;

import android.content.ContentValues;

import java.io.Serializable;

public class Video implements Serializable {

    private long _id;
    private String titulo;
    private String categoria;
    private String subcategoria;
    private String descripcion;
    private String filePath;
    private long offset;
    private long totalSize;
    private String calidad;
    private String segundos;
    private String resolucion;
    private String userId;
    private String url;

    public Video() {
    }

    public Video(int _id, String titulo, String descripcion, String categoria, String subcategoria, String filePath, long offset,
                 long totalSize, String calidad, String segundos, String resolucion, String url, String userId) {
        super();
        this._id = _id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.subcategoria = subcategoria;
        this.filePath = filePath;
        this.offset = offset;
        this.totalSize = totalSize;
        this.calidad = calidad;
        this.segundos = segundos;
        this.resolucion = resolucion;
        this.url = url;
        this.userId = userId;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getSubcategoria() {
        return subcategoria;
    }

    public void setSubcategoria(String subcategoria) {
        this.subcategoria = subcategoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public long getOffset() {
        return offset;
    }

    public void setOffset(long offset) {
        this.offset = offset;
    }

    public String getCalidad() {
        return calidad;
    }

    public void setCalidad(String calidad) {
        this.calidad = calidad;
    }

    public String getSegundos() {
        return segundos;
    }

    public void setSegundos(String segundos) {
        this.segundos = segundos;
    }

    public String getResolucion() {
        return resolucion;
    }

    public void setResolucion(String resolucion) {
        this.resolucion = resolucion;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(long totalSize) {
        this.totalSize = totalSize;
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(VideosContract.VideosEntry.COLUMN_NAME_CATEGORY, getCategoria());
        values.put(VideosContract.VideosEntry.COLUMN_NAME_DESCRIPTION, getDescripcion());
        values.put(VideosContract.VideosEntry.COLUMN_NAME_FILE_PATH, getFilePath());
        values.put(VideosContract.VideosEntry.COLUMN_NAME_OFFSET, getOffset());
        values.put(VideosContract.VideosEntry.COLUMN_NAME_QUALITY, getCalidad());
        values.put(VideosContract.VideosEntry.COLUMN_NAME_RESOLUTION, getResolucion());
        values.put(VideosContract.VideosEntry.COLUMN_NAME_SECONDS, getSegundos());
        values.put(VideosContract.VideosEntry.COLUMN_NAME_SUBCATEGORY, getSubcategoria());
        values.put(VideosContract.VideosEntry.COLUMN_NAME_TITLE, getTitulo());
        values.put(VideosContract.VideosEntry.COLUMN_NAME_URL, getUrl());
        values.put(VideosContract.VideosEntry.COLUMN_NAME_USER_ID, getUserId());
        values.put(VideosContract.VideosEntry.COLUMN_NAME_TOTAL_SIZE, getTotalSize());
        return values;
    }
}
