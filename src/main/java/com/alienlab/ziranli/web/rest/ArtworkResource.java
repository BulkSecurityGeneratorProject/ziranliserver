package com.alienlab.ziranli.web.rest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alienlab.ziranli.domain.ArtworkImage;
import com.alienlab.ziranli.web.rest.util.ExecResult;
import com.codahale.metrics.annotation.Timed;
import com.alienlab.ziranli.domain.Artwork;
import com.alienlab.ziranli.service.ArtworkService;
import com.alienlab.ziranli.web.rest.util.HeaderUtil;
import com.alienlab.ziranli.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Artwork.
 */
@RestController
@RequestMapping("/api")
public class ArtworkResource {

    private final Logger log = LoggerFactory.getLogger(ArtworkResource.class);

    private static final String ENTITY_NAME = "artwork";

    private final ArtworkService artworkService;

    public ArtworkResource(ArtworkService artworkService) {
        this.artworkService = artworkService;
    }

    /**
     * POST  /artworks : Create a new artwork.
     *
     * @param artwork the artwork to create
     * @return the ResponseEntity with status 201 (Created) and with body the new artwork, or with status 400 (Bad Request) if the artwork has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/artworks")
    @Timed
    public ResponseEntity<Artwork> createArtwork(@RequestBody Artwork artwork) throws URISyntaxException {
        log.debug("REST request to save Artwork : {}", artwork);
        if (artwork.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new artwork cannot already have an ID")).body(null);
        }
        Artwork result = artworkService.save(artwork);
        return ResponseEntity.created(new URI("/api/artworks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /artworks : Updates an existing artwork.
     *
     * @param artwork the artwork to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated artwork,
     * or with status 400 (Bad Request) if the artwork is not valid,
     * or with status 500 (Internal Server Error) if the artwork couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/artworks")
    @Timed
    public ResponseEntity<Artwork> updateArtwork(@RequestBody Artwork artwork) throws URISyntaxException {
        log.debug("REST request to update Artwork : {}", artwork);
        if (artwork.getId() == null) {
            return createArtwork(artwork);
        }
        Artwork result = artworkService.save(artwork);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, artwork.getId().toString()))
            .body(result);
    }

    /**
     * GET  /artworks : get all the artworks.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of artworks in body
     */
    @GetMapping("/artworks")
    @Timed
    public ResponseEntity<List<Artwork>> getAllArtworks(@ApiParam Pageable pageable) {
        System.out.println(pageable);
        log.debug("REST request to get a page of Artworks");
        Page<Artwork> page = artworkService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/artworks");
        System.out.println(headers);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /artworks/:id : get the "id" artwork.
     *
     * @param id the id of the artwork to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the artwork, or with status 404 (Not Found)
     */
    @GetMapping("/artworks/{id}")
    @Timed
    public ResponseEntity<Artwork> getArtwork(@PathVariable Long id) {
        log.debug("REST request to get Artwork : {}", id);
        Artwork artwork = artworkService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(artwork));
    }

    /**
     * DELETE  /artworks/:id : delete the "id" artwork.
     *
     * @param id the id of the artwork to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/artworks/{id}")
    @Timed
    public ResponseEntity<Void> deleteArtwork(@PathVariable Long id) {
        log.debug("REST request to delete Artwork : {}", id);
        artworkService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @ApiOperation("获取艺术品的对应的图册")
    @GetMapping("/artwork/images/{id}")
    @Timed
    public ResponseEntity loadArtworkImages(@PathVariable Long id) {
        log.debug("load artwork images : {}", id);
        try {
            List<ArtworkImage> images = artworkService.loadImages(id);
            return ResponseEntity.ok().body(images);
        } catch (Exception e) {
            e.printStackTrace();
            ExecResult er = new ExecResult(false, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }
    }

    @ApiOperation("不分页获取所有艺术品")
    @GetMapping("/allArtworks")
    @Timed
    public ResponseEntity<List<Artwork>> findAll() {
        log.debug("不分页获取所有艺术品 : {}");
        List<Artwork> artworks = artworkService.getAll();
        return ResponseEntity.ok().body(artworks);
    }

    @ApiOperation("获取所有艺术品类别")
    @GetMapping("/allTypes")
    @Timed
    public ResponseEntity<List<String>> findAllTypes() {
        log.debug("获取所有艺术品类别 : {}");
        List<String> types = artworkService.getAllType();
        return ResponseEntity.ok().body(types);
    }

    @ApiOperation("获取所有艺术品材质")
    @GetMapping("/allMaterials")
    @Timed
    public ResponseEntity<List<String>> findAllMaterials() {
        log.debug("获取所有艺术品材质 : {}");
        List<String> materials = artworkService.getAllMaterial();
        return ResponseEntity.ok().body(materials);
    }

    @ApiOperation("获取所有艺术品尺寸")
    @GetMapping("/allSizes")
    @Timed
    public ResponseEntity<List<String>> findAllSizes() {
        log.debug("获取所有艺术品尺寸 : {}");
        List<String> sizes = artworkService.getAllSize();
        return ResponseEntity.ok().body(sizes);
    }

    @ApiOperation("根据类型获取艺术品")
    @GetMapping("/byType/{type}")
    @Timed
    public ResponseEntity<List<Artwork>> findByType(@PathVariable String type) {
        log.debug("根据类型获取艺术品 : {}");
        List<Artwork> artworks = artworkService.getByType(type);
        return ResponseEntity.ok().body(artworks);
    }

    @ApiOperation("根据尺寸获取艺术品")
    @GetMapping("/bySize/{size}")
    @Timed
    public ResponseEntity<List<Artwork>> findBySize(@PathVariable String size) {
        log.debug("根据尺寸获取艺术品 : {}");
        List<Artwork> artworks = artworkService.getBySize(size);
        return ResponseEntity.ok().body(artworks);
    }

    @ApiOperation("根据材质获取艺术品")
    @GetMapping("/byMaterial/{material}")
    @Timed
    public ResponseEntity<List<Artwork>> findByMaterial(@PathVariable String material) {
        log.debug("根据材质获取艺术品 : {}");
        List<Artwork> artworks = artworkService.getByMaterial(material);
        return ResponseEntity.ok().body(artworks);
    }

    @ApiOperation("根据类别和材质获取艺术品")
    @GetMapping("/byTypeAndMaterial/{type}-{material}")
    @Timed
    public ResponseEntity<List<Artwork>> findByTypeAndMaterial(@PathVariable String type, @PathVariable String material) {
        log.debug("根据类别和材质获取艺术品 : {}");
        List<Artwork> artworks = artworkService.getByTypeAndMaterial(type, material);
        return ResponseEntity.ok().body(artworks);
    }

    @ApiOperation("根据类别和尺寸获取艺术品")
    @GetMapping("/byTypeAndSize/{type}-{size}")
    @Timed
    public ResponseEntity<List<Artwork>> findByTypeAndSize(@PathVariable String type, @PathVariable String size) {
        log.debug("根据类别和尺寸获取艺术品 : {}");
        List<Artwork> artworks = artworkService.getByTypeAndSize(type, size);
        return ResponseEntity.ok().body(artworks);
    }

    @ApiOperation("根据材质和尺寸获取艺术品")
    @GetMapping("/byMaterialAndSize/{material}-{size}")
    @Timed
    public ResponseEntity<List<Artwork>> findByMaterialAndSize(@PathVariable String material, @PathVariable String size) {
        log.debug("根据材质和尺寸获取艺术品 : {}");
        List<Artwork> artworks = artworkService.getByMaterialAndSize(material, size);
        return ResponseEntity.ok().body(artworks);
    }

    @ApiOperation("根据类别、材质和尺寸获取艺术品")
    @GetMapping("/byTypeAndMaterialAndSize/{type}-{material}-{size}")
    @Timed
    public ResponseEntity<List<Artwork>> findByTypeAndMaterialAndSize(@PathVariable String type, @PathVariable String material, @PathVariable String size) {
        log.debug("根据类别、材质和尺寸获取艺术品 : {}");
        List<Artwork> artworks = artworkService.getByTypeAndMaterialAndSize(type, material, size);
        return ResponseEntity.ok().body(artworks);
    }
}
