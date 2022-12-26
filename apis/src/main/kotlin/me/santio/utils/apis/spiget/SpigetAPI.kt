package me.santio.utils.apis.spiget

import kong.unirest.Unirest
import me.santio.utils.apis.APIUtils
import me.santio.utils.apis.spiget.models.author.SpigetAuthorModel
import me.santio.utils.apis.spiget.models.category.SpigetCategoryModel
import me.santio.utils.apis.spiget.models.resource.SpigetResourceModel
import me.santio.utils.apis.spiget.models.review.SpigetReviewModel
import me.santio.utils.apis.spiget.types.impl.SpigetAuthor
import me.santio.utils.apis.spiget.types.impl.SpigetCategory
import me.santio.utils.apis.spiget.types.impl.SpigetResource
import me.santio.utils.apis.spiget.types.impl.SpigetReview
import java.util.Base64

/**
 * An object to interact with Spiget API.
 * Keep in mind all methods in this dependency are not thread safe.
 */
object SpigetAPI {

    const val BASE_URL = "https://api.spiget.org/v2"
    private var categories: List<SpigetCategory> = emptyList()

    /**
     * Get an author by their id.
     * @param id The id of the author.
     */
    @JvmStatic
    fun getAuthor(id: Int): SpigetAuthor {
        return Unirest.get("$BASE_URL/authors/$id")
            .asObject(SpigetAuthorModel::class.java)
            .body
            .convert()
    }

    /**
     * Search for authors by name
     *
     * @param name The name of the author
     * @return A search builder to further customize the search
     */
    @JvmStatic
    fun searchAuthors(name: String): SearchBuilder<SpigetAuthor, SpigetAuthorModel> {
        return SearchBuilder(SpigetAuthorModel::class.java, SpigetAuthor::class.java, "authors", name, setOf(
            "id", "name", "icon"
        ))
    }

    /**
     * Get reviews belonging to an author
     * @param author The author
     * @return A search builder to further customize the search
     */
    @JvmStatic
    fun getReviews(author: SpigetAuthor): SearchBuilder<SpigetReview, SpigetReviewModel> {
        return SearchBuilder(SpigetReviewModel::class.java, SpigetReview::class.java, "", "", setOf(
            "author", "rating", "message", "version", "date", "resource", "id"
        )).uri("$BASE_URL/authors/${author.id()}/reviews")
    }

    /**
     * Get all categories from Spiget API. This method will cache the results,
     * so you can use `uncache()` to clear the cache.
     * @apiNote This method could return duplicates, this is a Spiget API issue.
     * @return A list of all categories.
     */
    @JvmStatic
    fun getCategories(): List<SpigetCategory> {
        if (categories.isNotEmpty()) return categories

        @Suppress("UNCHECKED_CAST")
        categories = SearchBuilder(SpigetCategoryModel::class.java, SpigetCategory::class.java, "", "", setOf("id", "name"))
            .uri("$BASE_URL/categories")
            .limit(100)
            .sort("")
            .search()?.results() ?: emptyList()

        return categories
    }

    /**
     * Clear the cache of categories.
     */
    @Suppress("unused")
    @JvmStatic
    fun uncache() {
        categories = emptyList()
    }

    @JvmStatic
    fun getResource(id: Int): SpigetResource {
        return Unirest.get("$BASE_URL/resources/$id")
            .asObject(SpigetResourceModel::class.java)
            .body
            .convert()
    }

    @JvmStatic
    fun searchResources(name: String): SearchBuilder<SpigetResource, SpigetResourceModel> {
        return SearchBuilder(SpigetResourceModel::class.java, SpigetResource::class.java, "resources", name, setOf(
            "id", "name", "tag", "author", "reviews", "rating", "downloads", "price", "external",
            "icon", "premium", "currency", "category", "version", "versions", "updates", "file",
            "testedVersions", "links", "releaseDate", "updateDate", "contributors", "likes", "sourceCodeLink",
            "donationLink"
        ))
    }

    fun getResourceDescription(id: Int): String {
        val encoded = Unirest.get("$BASE_URL/resources/$id")
            .asJson()
            .body
            .`object`
            .getString("description")

        return Base64.getDecoder().decode(encoded).toString(Charsets.UTF_8)
    }

}