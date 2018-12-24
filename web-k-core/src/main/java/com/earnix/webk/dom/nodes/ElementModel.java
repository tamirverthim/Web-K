package com.earnix.webk.dom.nodes;

import com.earnix.webk.dom.helper.ChangeNotifyingArrayList;
import com.earnix.webk.dom.helper.Validate;
import com.earnix.webk.dom.internal.StringUtil;
import com.earnix.webk.dom.parser.ParseSettings;
import com.earnix.webk.dom.parser.Tag;
import com.earnix.webk.dom.select.Collector;
import com.earnix.webk.dom.select.Elements;
import com.earnix.webk.dom.select.Evaluator;
import com.earnix.webk.dom.select.NodeTraversor;
import com.earnix.webk.dom.select.NodeVisitor;
import com.earnix.webk.dom.select.QueryParser;
import com.earnix.webk.dom.select.Selector;
import com.earnix.webk.render.Box;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import static com.earnix.webk.dom.internal.Normalizer.normalize;

/**
 * A HTML element consists of a tag name, attributes, and child nodes (including text nodes and
 * other elements).
 * <p>
 * From an Element, you can extract data, traverse the node graph, and manipulate the HTML.
 *
 * @author Jonathan Hedley, jonathan@hedley.net
 */
public class ElementModel extends NodeModel {
    private static final List<NodeModel> EMPTY_NODES = Collections.emptyList();
    private static final Pattern classSplit = Pattern.compile("\\s+");
    private Tag tag;
    private WeakReference<List<ElementModel>> shadowChildrenRef; // points to child elements shadowed from node children
    List<NodeModel> childNodes;
    private AttributesModel attributes;
    private String baseUri;

    private WeakReference<Box> view = new WeakReference<>(null);

    public Box getView() {
        return view.get();
    }

    public ElementModel setView(Box view) {
        this.view = new WeakReference<>(view);
        return this;
    }

    /**
     * Create a new, standalone element.
     *
     * @param tag tag name
     */
    public ElementModel(String tag) {
        this(Tag.valueOf(tag), "", new AttributesModel());
    }

    /**
     * Create a new, standalone Element. (Standalone in that is has no parent.)
     *
     * @param tag        tag of this element
     * @param baseUri    the base URI
     * @param attributes initial attributes
     * @see #appendChild(NodeModel)
     * @see #appendElement(String)
     */
    public ElementModel(Tag tag, String baseUri, AttributesModel attributes) {
        Validate.notNull(tag);
        Validate.notNull(baseUri);
        childNodes = EMPTY_NODES;
        this.baseUri = baseUri;
        this.attributes = attributes;
        this.tag = tag;
    }

    /**
     * Create a new Element from a tag and a base URI.
     *
     * @param tag     element tag
     * @param baseUri the base URI of this element. It is acceptable for the base URI to be an empty
     *                string, but not null.
     * @see Tag#valueOf(String, ParseSettings)
     */
    public ElementModel(Tag tag, String baseUri) {
        this(tag, baseUri, null);
    }

    protected List<NodeModel> ensureChildNodes() {
        if (childNodes == EMPTY_NODES) {
            childNodes = new NodeList(this, 4);
        }
        return childNodes;
    }

    @Override
    protected boolean hasAttributes() {
        return attributes != null;
    }

    @Override
    public AttributesModel attributes() {
        if (!hasAttributes())
            attributes = new AttributesModel();
        return attributes;
    }

    @Override
    public String baseUri() {
        return baseUri;
    }

    @Override
    protected void doSetBaseUri(String baseUri) {
        this.baseUri = baseUri;
    }

    @Override
    public int childNodeSize() {
        return childNodes.size();
    }

    @Override
    public String nodeName() {
        return tag.getName();
    }

    /**
     * Get the name of the tag for this element. E.g. {@code div}
     *
     * @return the tag name
     */
    public String tagName() {
        return tag.getName();
    }

    /**
     * Change the tag of this element. For example, convert a {@code <span>} to a {@code <div>} with
     * {@code el.tagName("div");}.
     *
     * @param tagName new tag name for this element
     * @return this element, for chaining
     */
    public ElementModel tagName(String tagName) {
        Validate.notEmpty(tagName, "Tag name must not be empty.");
        tag = Tag.valueOf(tagName, NodeModelUtils.parser(this).settings()); // maintains the case option of the original parse
        return this;
    }

    /**
     * Get the Tag for this element.
     *
     * @return the tag object
     */
    public Tag tag() {
        return tag;
    }

    /**
     * Test if this element is a block-level element. (E.g. {@code <div> == true} or an inline element
     * {@code <p> == false}).
     *
     * @return true if block, false if not (and thus inline)
     */
    public boolean isBlock() {
        return tag.isBlock();
    }

    /**
     * Get the {@code id} attribute of this element.
     *
     * @return The id attribute, if present, or an empty string if not.
     */
    public String id() {
        return attributes().getIgnoreCase("id");
    }

    /**
     * Set an attribute value on this element. If this element already has an attribute with the
     * key, its value is updated; otherwise, a new attribute is added.
     *
     * @return this element
     */
    public ElementModel attr(String attributeKey, String attributeValue) {
        super.attr(attributeKey, attributeValue);
        return this;
    }

    /**
     * Set a boolean attribute value on this element. Setting to <code>true</code> sets the attribute value to "" and
     * marks the attribute as boolean so no value is written out. Setting to <code>false</code> removes the attribute
     * with the same key if it exists.
     *
     * @param attributeKey   the attribute key
     * @param attributeValue the attribute value
     * @return this element
     */
    public ElementModel attr(String attributeKey, boolean attributeValue) {
        attributes().put(attributeKey, attributeValue);
        return this;
    }

    /**
     * Get this element's HTML5 custom data attributes. Each attribute in the element that has a key
     * starting with "data-" is included the dataset.
     * <p>
     * E.g., the element {@code <div data-package="jsoup" data-language="Java" class="group">...} has the dataset
     * {@code package=jsoup, language=java}.
     * <p>
     * This map is a filtered view of the element's attribute map. Changes to one map (add, remove, update) are reflected
     * in the other map.
     * <p>
     * You can find elements that have data attributes using the {@code [^data-]} attribute key prefix selector.
     *
     * @return a map of {@code key=value} custom data attributes.
     */
    public Map<String, String> dataset() {
        return attributes().dataset();
    }

    @Override
    public final ElementModel parent() {
        return (ElementModel) parentNode;
    }

    /**
     * Get this element's parent and ancestors, up to the document root.
     *
     * @return this element's stack of parents, closest first.
     */
    public Elements parents() {
        Elements parents = new Elements();
        accumulateParents(this, parents);
        return parents;
    }

    private static void accumulateParents(ElementModel el, Elements parents) {
        ElementModel parent = el.parent();
        if (parent != null && !parent.tagName().equals("#root")) {
            parents.add(parent);
            accumulateParents(parent, parents);
        }
    }

    /**
     * Get a child element of this element, by its 0-based index number.
     * <p>
     * Note that an element can have both mixed Nodes and Elements as children. This method inspects
     * a filtered list of children that are elements, and the index is based on that filtered list.
     * </p>
     *
     * @param index the index number of the element to retrieve
     * @return the child element, if it exists, otherwise throws an {@code IndexOutOfBoundsException}
     * @see #childNode(int)
     */
    public ElementModel child(int index) {
        return childElementsList().get(index);
    }

    /**
     * Get this element's child elements.
     * <p>
     * This is effectively a filter on {@link #childNodes()} to get Element nodes.
     * </p>
     *
     * @return child elements. If this element has no children, returns an empty list.
     * @see #childNodes()
     */
    public Elements children() {
        return new Elements(childElementsList());
    }

    /**
     * Maintains a shadow copy of this element's child elements. If the nodelist is changed, this cache is invalidated.
     * TODO - think about pulling this out as a helper as there are other shadow lists (like in Attributes) kept around.
     *
     * @return a list of child elements
     */
    private List<ElementModel> childElementsList() {
        List<ElementModel> children;
        if (shadowChildrenRef == null || (children = shadowChildrenRef.get()) == null) {
            final int size = childNodes.size();
            children = new ArrayList<>(size);
            //noinspection ForLoopReplaceableByForEach (beacause it allocates an Iterator which is wasteful here)
            for (int i = 0; i < size; i++) {
                final NodeModel node = childNodes.get(i);
                if (node instanceof ElementModel)
                    children.add((ElementModel) node);
            }
            shadowChildrenRef = new WeakReference<>(children);
        }
        return children;
    }

    /**
     * Clears the cached shadow child elements.
     */
    @Override
    void nodelistChanged() {
        super.nodelistChanged();
        shadowChildrenRef = null;
    }

    /**
     * Get this element's child text nodes. The list is unmodifiable but the text nodes may be manipulated.
     * <p>
     * This is effectively a filter on {@link #childNodes()} to get Text nodes.
     *
     * @return child text nodes. If this element has no text nodes, returns an
     * empty list.
     * </p>
     * For example, with the input HTML: {@code <p>One <span>Two</span> Three <br> Four</p>} with the {@code p} element selected:
     * <ul>
     * <li>{@code p.text()} = {@code "One Two Three Four"}</li>
     * <li>{@code p.ownText()} = {@code "One Three Four"}</li>
     * <li>{@code p.children()} = {@code Elements[<span>, <br>]}</li>
     * <li>{@code p.childNodes()} = {@code List<Node>["One ", <span>, " Three ", <br>, " Four"]}</li>
     * <li>{@code p.textNodes()} = {@code List<TextNode>["One ", " Three ", " Four"]}</li>
     * </ul>
     */
    public List<TextNodeModel> textNodes() {
        List<TextNodeModel> textNodes = new ArrayList<>();
        for (NodeModel node : childNodes) {
            if (node instanceof TextNodeModel)
                textNodes.add((TextNodeModel) node);
        }
        return Collections.unmodifiableList(textNodes);
    }

    /**
     * Get this element's child data nodes. The list is unmodifiable but the data nodes may be manipulated.
     * <p>
     * This is effectively a filter on {@link #childNodes()} to get Data nodes.
     * </p>
     *
     * @return child data nodes. If this element has no data nodes, returns an
     * empty list.
     * @see #data()
     */
    public List<DataNodeModel> dataNodes() {
        List<DataNodeModel> dataNodes = new ArrayList<>();
        for (NodeModel node : childNodes) {
            if (node instanceof DataNodeModel)
                dataNodes.add((DataNodeModel) node);
        }
        return Collections.unmodifiableList(dataNodes);
    }

    /**
     * Find elements that match the {@link Selector} CSS query, with this element as the starting context. Matched elements
     * may include this element, or any of its children.
     * <p>
     * This method is generally more powerful to use than the DOM-type {@code getElementBy*} methods, because
     * multiple filters can be combined, e.g.:
     * </p>
     * <ul>
     * <li>{@code el.select("a[href]")} - finds links ({@code a} tags with {@code href} attributes)
     * <li>{@code el.select("a[href*=example.com]")} - finds links pointing to example.com (loosely)
     * </ul>
     * <p>
     * See the query syntax documentation in {@link com.earnix.webk.dom.select.Selector}.
     * </p>
     *
     * @param cssQuery a {@link Selector} CSS-like query
     * @return elements that match the query (empty if none match)
     * @throws Selector.SelectorParseException (unchecked) on an invalid CSS query.
     * @see com.earnix.webk.dom.select.Selector
     */
    public Elements select(String cssQuery) {
        return Selector.select(cssQuery, this);
    }

    /**
     * Find the first Element that matches the {@link Selector} CSS query, with this element as the starting context.
     * <p>This is effectively the same as calling {@code element.select(query).first()}, but is more efficient as query
     * execution stops on the first hit.</p>
     *
     * @param cssQuery cssQuery a {@link Selector} CSS-like query
     * @return the first matching element, or <b>{@code null}</b> if there is no match.
     */
    public ElementModel selectFirst(String cssQuery) {
        return Selector.selectFirst(cssQuery, this);
    }

    /**
     * Check if this element matches the given {@link Selector} CSS query.
     *
     * @param cssQuery a {@link Selector} CSS query
     * @return if this element matches the query
     */
    public boolean is(String cssQuery) {
        return is(QueryParser.parse(cssQuery));
    }

    /**
     * Check if this element matches the given evaluator.
     *
     * @param evaluator an element evaluator
     * @return if this element matches
     */
    public boolean is(Evaluator evaluator) {
        return evaluator.matches((ElementModel) this.root(), this);
    }

    /**
     * Add a node child node to this element.
     *
     * @param child node to add.
     * @return this element, so that you can add more child nodes or elements.
     */
    public ElementModel appendChild(NodeModel child) {
        Validate.notNull(child);

        // was - Node#addChildren(child). short-circuits an array create and a loop.
        reparentChild(child);
        ensureChildNodes();
        childNodes.add(child);
        child.setSiblingIndex(childNodes.size() - 1);
        return this;
    }

    /**
     * Add this element to the supplied parent element, as its next child.
     *
     * @param parent element to which this element will be appended
     * @return this element, so that you can continue modifying the element
     */
    public ElementModel appendTo(ElementModel parent) {
        Validate.notNull(parent);
        parent.appendChild(this);
        return this;
    }

    /**
     * Add a node to the start of this element's children.
     *
     * @param child node to add.
     * @return this element, so that you can add more child nodes or elements.
     */
    public ElementModel prependChild(NodeModel child) {
        Validate.notNull(child);

        addChildren(0, child);
        return this;
    }


    /**
     * Inserts the given child nodes into this element at the specified index. Current nodes will be shifted to the
     * right. The inserted nodes will be moved from their current parent. To prevent moving, copy the nodes first.
     *
     * @param index    0-based index to insert children at. Specify {@code 0} to insert at the start, {@code -1} at the
     *                 end
     * @param children child nodes to insert
     * @return this element, for chaining.
     */
    public ElementModel insertChildren(int index, Collection<? extends NodeModel> children) {
        Validate.notNull(children, "Children collection to be inserted must not be null.");
        int currentSize = childNodeSize();
        if (index < 0) index += currentSize + 1; // roll around
        Validate.isTrue(index >= 0 && index <= currentSize, "Insert position out of bounds.");

        ArrayList<NodeModel> nodes = new ArrayList<>(children);
        NodeModel[] nodeArray = nodes.toArray(new NodeModel[nodes.size()]);
        addChildren(index, nodeArray);
        return this;
    }

    /**
     * Inserts the given child nodes into this element at the specified index. Current nodes will be shifted to the
     * right. The inserted nodes will be moved from their current parent. To prevent moving, copy the nodes first.
     *
     * @param index    0-based index to insert children at. Specify {@code 0} to insert at the start, {@code -1} at the
     *                 end
     * @param children child nodes to insert
     * @return this element, for chaining.
     */
    public ElementModel insertChildren(int index, NodeModel... children) {
        Validate.notNull(children, "Children collection to be inserted must not be null.");
        int currentSize = childNodeSize();
        if (index < 0) index += currentSize + 1; // roll around
        Validate.isTrue(index >= 0 && index <= currentSize, "Insert position out of bounds.");

        addChildren(index, children);
        return this;
    }

    /**
     * Create a new element by tag name, and add it as the last child.
     *
     * @param tagName the name of the tag (e.g. {@code div}).
     * @return the new element, to allow you to add content to it, e.g.:
     * {@code parent.appendElement("h1").attr("id", "header").text("Welcome");}
     */
    public ElementModel appendElement(String tagName) {
        ElementModel child = new ElementModel(Tag.valueOf(tagName, NodeModelUtils.parser(this).settings()), baseUri());
        appendChild(child);
        return child;
    }

    /**
     * Create a new element by tag name, and add it as the first child.
     *
     * @param tagName the name of the tag (e.g. {@code div}).
     * @return the new element, to allow you to add content to it, e.g.:
     * {@code parent.prependElement("h1").attr("id", "header").text("Welcome");}
     */
    public ElementModel prependElement(String tagName) {
        ElementModel child = new ElementModel(Tag.valueOf(tagName, NodeModelUtils.parser(this).settings()), baseUri());
        prependChild(child);
        return child;
    }

    /**
     * Create and append a new TextNode to this element.
     *
     * @param text the unencoded text to add
     * @return this element
     */
    public ElementModel appendText(String text) {
        Validate.notNull(text);
        TextNodeModel node = new TextNodeModel(text);
        appendChild(node);
        return this;
    }

    /**
     * Create and prepend a new TextNode to this element.
     *
     * @param text the unencoded text to add
     * @return this element
     */
    public ElementModel prependText(String text) {
        Validate.notNull(text);
        TextNodeModel node = new TextNodeModel(text);
        prependChild(node);
        return this;
    }

    /**
     * Add inner HTML to this element. The supplied HTML will be parsed, and each node appended to the end of the children.
     *
     * @param html HTML to add inside this element, after the existing HTML
     * @return this element
     * @see #html(String)
     */
    public ElementModel append(String html) {
        Validate.notNull(html);
        List<NodeModel> nodes = NodeModelUtils.parser(this).parseFragmentInput(html, this, baseUri());
        addChildren(nodes.toArray(new NodeModel[nodes.size()]));
        return this;
    }

    /**
     * Add inner HTML into this element. The supplied HTML will be parsed, and each node prepended to the start of the element's children.
     *
     * @param html HTML to add inside this element, before the existing HTML
     * @return this element
     * @see #html(String)
     */
    public ElementModel prepend(String html) {
        Validate.notNull(html);
        List<NodeModel> nodes = NodeModelUtils.parser(this).parseFragmentInput(html, this, baseUri());
        addChildren(0, nodes.toArray(new NodeModel[nodes.size()]));
        return this;
    }

    /**
     * Insert the specified HTML into the DOM before this element (as a preceding sibling).
     *
     * @param html HTML to add before this element
     * @return this element, for chaining
     * @see #after(String)
     */
    @Override
    public ElementModel before(String html) {
        return (ElementModel) super.before(html);
    }

    /**
     * Insert the specified node into the DOM before this node (as a preceding sibling).
     *
     * @param node to add before this element
     * @return this Element, for chaining
     * @see #after(NodeModel)
     */
    @Override
    public ElementModel before(NodeModel node) {
        return (ElementModel) super.before(node);
    }

    /**
     * Insert the specified HTML into the DOM after this element (as a following sibling).
     *
     * @param html HTML to add after this element
     * @return this element, for chaining
     * @see #before(String)
     */
    @Override
    public ElementModel after(String html) {
        return (ElementModel) super.after(html);
    }

    /**
     * Insert the specified node into the DOM after this node (as a following sibling).
     *
     * @param node to add after this element
     * @return this element, for chaining
     * @see #before(NodeModel)
     */
    @Override
    public ElementModel after(NodeModel node) {
        return (ElementModel) super.after(node);
    }

    /**
     * Remove all of the element's child nodes. Any attributes are left as-is.
     *
     * @return this element
     */
    public ElementModel empty() {
        childNodes.clear();
        return this;
    }

    /**
     * Wrap the supplied HTML around this element.
     *
     * @param html HTML to wrap around this element, e.g. {@code <div class="head"></div>}. Can be arbitrarily deep.
     * @return this element, for chaining.
     */
    @Override
    public ElementModel wrap(String html) {
        return (ElementModel) super.wrap(html);
    }

    /**
     * Get a CSS selector that will uniquely select this element.
     * <p>
     * If the element has an ID, returns #id;
     * otherwise returns the parent (if any) CSS selector, followed by {@literal '>'},
     * followed by a unique selector for the element (tag.class.class:nth-child(n)).
     * </p>
     *
     * @return the CSS Path that can be used to retrieve the element in a selector.
     */
    public String cssSelector() {
        if (id().length() > 0)
            return "#" + id();

        // Translate HTML namespace ns:tag to CSS namespace syntax ns|tag
        String tagName = tagName().replace(':', '|');
        StringBuilder selector = new StringBuilder(tagName);
        String classes = StringUtil.join(classNames(), ".");
        if (classes.length() > 0)
            selector.append('.').append(classes);

        if (parent() == null || parent() instanceof DocumentModel) // don't add Document to selector, as will always have a html node
            return selector.toString();

        selector.insert(0, " > ");
        if (parent().select(selector.toString()).size() > 1)
            selector.append(String.format(
                    ":nth-child(%d)", elementSiblingIndex() + 1));

        return parent().cssSelector() + selector.toString();
    }

    /**
     * Get sibling elements. If the element has no sibling elements, returns an empty list. An element is not a sibling
     * of itself, so will not be included in the returned list.
     *
     * @return sibling elements
     */
    public Elements siblingElements() {
        if (parentNode == null)
            return new Elements(0);

        List<ElementModel> elements = parent().childElementsList();
        Elements siblings = new Elements(elements.size() - 1);
        for (ElementModel el : elements)
            if (el != this)
                siblings.add(el);
        return siblings;
    }

    /**
     * Gets the next sibling element of this element. E.g., if a {@code div} contains two {@code p}s,
     * the {@code nextElementSibling} of the first {@code p} is the second {@code p}.
     * <p>
     * This is similar to {@link #nextSibling()}, but specifically finds only Elements
     * </p>
     *
     * @return the next element, or null if there is no next element
     * @see #previousElementSibling()
     */
    public ElementModel nextElementSibling() {
        if (parentNode == null) return null;
        List<ElementModel> siblings = parent().childElementsList();
        Integer index = indexInList(this, siblings);
        Validate.notNull(index);
        if (siblings.size() > index + 1)
            return siblings.get(index + 1);
        else
            return null;
    }

    /**
     * Get each of the sibling elements that come after this element.
     *
     * @return each of the element siblings after this element, or an empty list if there are no next sibling elements
     */
    public Elements nextElementSiblings() {
        return nextElementSiblings(true);
    }

    /**
     * Gets the previous element sibling of this element.
     *
     * @return the previous element, or null if there is no previous element
     * @see #nextElementSibling()
     */
    public ElementModel previousElementSibling() {
        if (parentNode == null) return null;
        List<ElementModel> siblings = parent().childElementsList();
        Integer index = indexInList(this, siblings);
        Validate.notNull(index);
        if (index > 0)
            return siblings.get(index - 1);
        else
            return null;
    }

    /**
     * Get each of the element siblings before this element.
     *
     * @return the previous element siblings, or an empty list if there are none.
     */
    public Elements previousElementSiblings() {
        return nextElementSiblings(false);
    }

    private Elements nextElementSiblings(boolean next) {
        Elements els = new Elements();
        if (parentNode == null)
            return els;
        els.add(this);
        return next ? els.nextAll() : els.prevAll();
    }

    /**
     * Gets the first element sibling of this element.
     *
     * @return the first sibling that is an element (aka the parent's first element child)
     */
    public ElementModel firstElementSibling() {
        // todo: should firstSibling() exclude this?
        List<ElementModel> siblings = parent().childElementsList();
        return siblings.size() > 1 ? siblings.get(0) : null;
    }

    /**
     * Get the list index of this element in its element sibling list. I.e. if this is the first element
     * sibling, returns 0.
     *
     * @return position in element sibling list
     */
    public int elementSiblingIndex() {
        if (parent() == null) return 0;
        return indexInList(this, parent().childElementsList());
    }

    /**
     * Gets the last element sibling of this element
     *
     * @return the last sibling that is an element (aka the parent's last element child)
     */
    public ElementModel lastElementSibling() {
        List<ElementModel> siblings = parent().childElementsList();
        return siblings.size() > 1 ? siblings.get(siblings.size() - 1) : null;
    }

    private static <E extends ElementModel> int indexInList(ElementModel search, List<E> elements) {
        final int size = elements.size();
        for (int i = 0; i < size; i++) {
            if (elements.get(i) == search)
                return i;
        }
        return 0;
    }

    // DOM type methods

    /**
     * Finds elements, including and recursively under this element, with the specified tag name.
     *
     * @param tagName The tag name to search for (case insensitively).
     * @return a matching unmodifiable list of elements. Will be empty if this element and none of its children match.
     */
    public Elements getElementsByTag(String tagName) {
        Validate.notEmpty(tagName);
        tagName = normalize(tagName);

        return Collector.collect(new Evaluator.Tag(tagName), this);
    }

    /**
     * Find an element by ID, including or under this element.
     * <p>
     * Note that this finds the first matching ID, starting with this element. If you search down from a different
     * starting point, it is possible to find a different element by ID. For unique element by ID within a Document,
     * use {@link DocumentModel#getElementById(String)}
     *
     * @param id The ID to search for.
     * @return The first matching element by ID, starting with this element, or null if none found.
     */
    public ElementModel getElementById(String id) {
        Validate.notEmpty(id);

        Elements elements = Collector.collect(new Evaluator.Id(id), this);
        if (elements.size() > 0)
            return elements.get(0);
        else
            return null;
    }

    /**
     * Find elements that have this class, including or under this element. Case insensitive.
     * <p>
     * Elements can have multiple classes (e.g. {@code <div class="header round first">}. This method
     * checks each class, so you can find the above with {@code el.getElementsByClass("header");}.
     *
     * @param className the name of the class to search for.
     * @return elements with the supplied class name, empty if none
     * @see #hasClass(String)
     * @see #classNames()
     */
    public Elements getElementsByClass(String className) {
        Validate.notEmpty(className);

        return Collector.collect(new Evaluator.Class(className), this);
    }

    /**
     * Find elements that have a named attribute set. Case insensitive.
     *
     * @param key name of the attribute, e.g. {@code href}
     * @return elements that have this attribute, empty if none
     */
    public Elements getElementsByAttribute(String key) {
        Validate.notEmpty(key);
        key = key.trim();

        return Collector.collect(new Evaluator.Attribute(key), this);
    }

    /**
     * Find elements that have an attribute name starting with the supplied prefix. Use {@code data-} to find elements
     * that have HTML5 datasets.
     *
     * @param keyPrefix name prefix of the attribute e.g. {@code data-}
     * @return elements that have attribute names that start with with the prefix, empty if none.
     */
    public Elements getElementsByAttributeStarting(String keyPrefix) {
        Validate.notEmpty(keyPrefix);
        keyPrefix = keyPrefix.trim();

        return Collector.collect(new Evaluator.AttributeStarting(keyPrefix), this);
    }

    /**
     * Find elements that have an attribute with the specific value. Case insensitive.
     *
     * @param key   name of the attribute
     * @param value value of the attribute
     * @return elements that have this attribute with this value, empty if none
     */
    public Elements getElementsByAttributeValue(String key, String value) {
        return Collector.collect(new Evaluator.AttributeWithValue(key, value), this);
    }

    /**
     * Find elements that either do not have this attribute, or have it with a different value. Case insensitive.
     *
     * @param key   name of the attribute
     * @param value value of the attribute
     * @return elements that do not have a matching attribute
     */
    public Elements getElementsByAttributeValueNot(String key, String value) {
        return Collector.collect(new Evaluator.AttributeWithValueNot(key, value), this);
    }

    /**
     * Find elements that have attributes that start with the value prefix. Case insensitive.
     *
     * @param key         name of the attribute
     * @param valuePrefix start of attribute value
     * @return elements that have attributes that start with the value prefix
     */
    public Elements getElementsByAttributeValueStarting(String key, String valuePrefix) {
        return Collector.collect(new Evaluator.AttributeWithValueStarting(key, valuePrefix), this);
    }

    /**
     * Find elements that have attributes that end with the value suffix. Case insensitive.
     *
     * @param key         name of the attribute
     * @param valueSuffix end of the attribute value
     * @return elements that have attributes that end with the value suffix
     */
    public Elements getElementsByAttributeValueEnding(String key, String valueSuffix) {
        return Collector.collect(new Evaluator.AttributeWithValueEnding(key, valueSuffix), this);
    }

    /**
     * Find elements that have attributes whose value contains the match string. Case insensitive.
     *
     * @param key   name of the attribute
     * @param match substring of value to search for
     * @return elements that have attributes containing this text
     */
    public Elements getElementsByAttributeValueContaining(String key, String match) {
        return Collector.collect(new Evaluator.AttributeWithValueContaining(key, match), this);
    }

    /**
     * Find elements that have attributes whose values match the supplied regular expression.
     *
     * @param key     name of the attribute
     * @param pattern compiled regular expression to match against attribute values
     * @return elements that have attributes matching this regular expression
     */
    public Elements getElementsByAttributeValueMatching(String key, Pattern pattern) {
        return Collector.collect(new Evaluator.AttributeWithValueMatching(key, pattern), this);

    }

    /**
     * Find elements that have attributes whose values match the supplied regular expression.
     *
     * @param key   name of the attribute
     * @param regex regular expression to match against attribute values. You can use <a href="http://java.sun.com/docs/books/tutorial/essential/regex/pattern.html#embedded">embedded flags</a> (such as (?i) and (?m) to control regex options.
     * @return elements that have attributes matching this regular expression
     */
    public Elements getElementsByAttributeValueMatching(String key, String regex) {
        Pattern pattern;
        try {
            pattern = Pattern.compile(regex);
        } catch (PatternSyntaxException e) {
            throw new IllegalArgumentException("Pattern syntax error: " + regex, e);
        }
        return getElementsByAttributeValueMatching(key, pattern);
    }

    /**
     * Find elements whose sibling index is less than the supplied index.
     *
     * @param index 0-based index
     * @return elements less than index
     */
    public Elements getElementsByIndexLessThan(int index) {
        return Collector.collect(new Evaluator.IndexLessThan(index), this);
    }

    /**
     * Find elements whose sibling index is greater than the supplied index.
     *
     * @param index 0-based index
     * @return elements greater than index
     */
    public Elements getElementsByIndexGreaterThan(int index) {
        return Collector.collect(new Evaluator.IndexGreaterThan(index), this);
    }

    /**
     * Find elements whose sibling index is equal to the supplied index.
     *
     * @param index 0-based index
     * @return elements equal to index
     */
    public Elements getElementsByIndexEquals(int index) {
        return Collector.collect(new Evaluator.IndexEquals(index), this);
    }

    /**
     * Find elements that contain the specified string. The search is case insensitive. The text may appear directly
     * in the element, or in any of its descendants.
     *
     * @param searchText to look for in the element's text
     * @return elements that contain the string, case insensitive.
     * @see ElementModel#text()
     */
    public Elements getElementsContainingText(String searchText) {
        return Collector.collect(new Evaluator.ContainsText(searchText), this);
    }

    /**
     * Find elements that directly contain the specified string. The search is case insensitive. The text must appear directly
     * in the element, not in any of its descendants.
     *
     * @param searchText to look for in the element's own text
     * @return elements that contain the string, case insensitive.
     * @see ElementModel#ownText()
     */
    public Elements getElementsContainingOwnText(String searchText) {
        return Collector.collect(new Evaluator.ContainsOwnText(searchText), this);
    }

    /**
     * Find elements whose text matches the supplied regular expression.
     *
     * @param pattern regular expression to match text against
     * @return elements matching the supplied regular expression.
     * @see ElementModel#text()
     */
    public Elements getElementsMatchingText(Pattern pattern) {
        return Collector.collect(new Evaluator.Matches(pattern), this);
    }

    /**
     * Find elements whose text matches the supplied regular expression.
     *
     * @param regex regular expression to match text against. You can use <a href="http://java.sun.com/docs/books/tutorial/essential/regex/pattern.html#embedded">embedded flags</a> (such as (?i) and (?m) to control regex options.
     * @return elements matching the supplied regular expression.
     * @see ElementModel#text()
     */
    public Elements getElementsMatchingText(String regex) {
        Pattern pattern;
        try {
            pattern = Pattern.compile(regex);
        } catch (PatternSyntaxException e) {
            throw new IllegalArgumentException("Pattern syntax error: " + regex, e);
        }
        return getElementsMatchingText(pattern);
    }

    /**
     * Find elements whose own text matches the supplied regular expression.
     *
     * @param pattern regular expression to match text against
     * @return elements matching the supplied regular expression.
     * @see ElementModel#ownText()
     */
    public Elements getElementsMatchingOwnText(Pattern pattern) {
        return Collector.collect(new Evaluator.MatchesOwn(pattern), this);
    }

    /**
     * Find elements whose text matches the supplied regular expression.
     *
     * @param regex regular expression to match text against. You can use <a href="http://java.sun.com/docs/books/tutorial/essential/regex/pattern.html#embedded">embedded flags</a> (such as (?i) and (?m) to control regex options.
     * @return elements matching the supplied regular expression.
     * @see ElementModel#ownText()
     */
    public Elements getElementsMatchingOwnText(String regex) {
        Pattern pattern;
        try {
            pattern = Pattern.compile(regex);
        } catch (PatternSyntaxException e) {
            throw new IllegalArgumentException("Pattern syntax error: " + regex, e);
        }
        return getElementsMatchingOwnText(pattern);
    }

    /**
     * Find all elements under this element (including self, and children of children).
     *
     * @return all elements
     */
    public Elements getAllElements() {
        return Collector.collect(new Evaluator.AllElements(), this);
    }

    /**
     * Gets the combined text of this element and all its children. Whitespace is normalized and trimmed.
     * <p>
     * For example, given HTML {@code <p>Hello  <b>there</b> now! </p>}, {@code p.text()} returns {@code "Hello there now!"}
     *
     * @return unencoded, normalized text, or empty string if none.
     * @see #wholeText() if you don't want the text to be normalized.
     * @see #ownText()
     * @see #textNodes()
     */
    public String text() {
        final StringBuilder accum = StringUtil.borrowBuilder();
        NodeTraversor.traverse(new NodeVisitor() {
            public void head(NodeModel node, int depth) {
                if (node instanceof TextNodeModel) {
                    TextNodeModel textNode = (TextNodeModel) node;
                    appendNormalisedText(accum, textNode);
                } else if (node instanceof ElementModel) {
                    ElementModel element = (ElementModel) node;
                    if (accum.length() > 0 &&
                            (element.isBlock() || element.tag.getName().equals("br")) &&
                            !TextNodeModel.lastCharIsWhitespace(accum))
                        accum.append(' ');
                }
            }

            public void tail(NodeModel node, int depth) {
                // make sure there is a space between block tags and immediately following text nodes <div>One</div>Two should be "One Two".
                if (node instanceof ElementModel) {
                    ElementModel element = (ElementModel) node;
                    if (element.isBlock() && (node.nextSibling() instanceof TextNodeModel) && !TextNodeModel.lastCharIsWhitespace(accum))
                        accum.append(' ');
                }

            }
        }, this);

        return StringUtil.releaseBuilder(accum).trim();
    }

    /**
     * Get the (unencoded) text of all children of this element, including any newlines and spaces present in the
     * original.
     *
     * @return unencoded, un-normalized text
     * @see #text()
     */
    public String wholeText() {
        final StringBuilder accum = StringUtil.borrowBuilder();
        NodeTraversor.traverse(new NodeVisitor() {
            public void head(NodeModel node, int depth) {
                if (node instanceof TextNodeModel) {
                    TextNodeModel textNode = (TextNodeModel) node;
                    accum.append(textNode.getWholeText());
                }
            }

            public void tail(NodeModel node, int depth) {
            }
        }, this);

        return StringUtil.releaseBuilder(accum);
    }

    /**
     * Gets the text owned by this element only; does not get the combined text of all children.
     * <p>
     * For example, given HTML {@code <p>Hello <b>there</b> now!</p>}, {@code p.ownText()} returns {@code "Hello now!"},
     * whereas {@code p.text()} returns {@code "Hello there now!"}.
     * Note that the text within the {@code b} element is not returned, as it is not a direct child of the {@code p} element.
     *
     * @return unencoded text, or empty string if none.
     * @see #text()
     * @see #textNodes()
     */
    public String ownText() {
        StringBuilder sb = StringUtil.borrowBuilder();
        ownText(sb);
        return StringUtil.releaseBuilder(sb).trim();
    }

    private void ownText(StringBuilder accum) {
        for (NodeModel child : childNodes) {
            if (child instanceof TextNodeModel) {
                TextNodeModel textNode = (TextNodeModel) child;
                appendNormalisedText(accum, textNode);
            } else if (child instanceof ElementModel) {
                appendWhitespaceIfBr((ElementModel) child, accum);
            }
        }
    }

    private static void appendNormalisedText(StringBuilder accum, TextNodeModel textNode) {
        String text = textNode.getWholeText();

        if (preserveWhitespace(textNode.parentNode) || textNode instanceof CDataNodeModel)
            accum.append(text);
        else
            StringUtil.appendNormalisedWhitespace(accum, text, TextNodeModel.lastCharIsWhitespace(accum));
    }

    private static void appendWhitespaceIfBr(ElementModel element, StringBuilder accum) {
        if (element.tag.getName().equals("br") && !TextNodeModel.lastCharIsWhitespace(accum))
            accum.append(" ");
    }

    static boolean preserveWhitespace(NodeModel node) {
        // looks only at this element and five levels up, to prevent recursion & needless stack searches
        if (node instanceof ElementModel) {
            ElementModel el = (ElementModel) node;
            int i = 0;
            do {
                if (el.tag.preserveWhitespace())
                    return true;
                el = el.parent();
                i++;
            } while (i < 6 && el != null);
        }
        return false;
    }

    /**
     * Set the text of this element. Any existing contents (text or elements) will be cleared
     *
     * @param text unencoded text
     * @return this element
     */
    public ElementModel text(String text) {
        Validate.notNull(text);

        empty();
        TextNodeModel textNode = new TextNodeModel(text);
        appendChild(textNode);

        return this;
    }

    /**
     * Test if this element has any text content (that is not just whitespace).
     *
     * @return true if element has non-blank text content.
     */
    public boolean hasText() {
        for (NodeModel child : childNodes) {
            if (child instanceof TextNodeModel) {
                TextNodeModel textNode = (TextNodeModel) child;
                if (!textNode.isBlank())
                    return true;
            } else if (child instanceof ElementModel) {
                ElementModel el = (ElementModel) child;
                if (el.hasText())
                    return true;
            }
        }
        return false;
    }

    /**
     * Get the combined data of this element. Data is e.g. the inside of a {@code script} tag. Note that data is NOT the
     * text of the element. Use {@link #text()} to get the text that would be visible to a user, and {@link #data()}
     * for the contents of scripts, comments, CSS styles, etc.
     *
     * @return the data, or empty string if none
     * @see #dataNodes()
     */
    public String data() {
        StringBuilder sb = StringUtil.borrowBuilder();

        for (NodeModel childNode : childNodes) {
            if (childNode instanceof DataNodeModel) {
                DataNodeModel data = (DataNodeModel) childNode;
                sb.append(data.getWholeData());
            } else if (childNode instanceof CommentModel) {
                CommentModel comment = (CommentModel) childNode;
                sb.append(comment.getData());
            } else if (childNode instanceof ElementModel) {
                ElementModel element = (ElementModel) childNode;
                String elementData = element.data();
                sb.append(elementData);
            } else if (childNode instanceof CDataNodeModel) {
                // this shouldn't really happen because the html parser won't see the cdata as anything special when parsing script.
                // but incase another type gets through.
                CDataNodeModel cDataNode = (CDataNodeModel) childNode;
                sb.append(cDataNode.getWholeText());
            }
        }
        return StringUtil.releaseBuilder(sb);
    }

    /**
     * Gets the literal value of this element's "class" attribute, which may include multiple class names, space
     * separated. (E.g. on <code>&lt;div class="header gray"&gt;</code> returns, "<code>header gray</code>")
     *
     * @return The literal class attribute, or <b>empty string</b> if no class attribute set.
     */
    public String className() {
        return attr("class").trim();
    }

    /**
     * Get all of the element's class names. E.g. on element {@code <div class="header gray">},
     * returns a set of two elements {@code "header", "gray"}. Note that modifications to this set are not pushed to
     * the backing {@code class} attribute; use the {@link #classNames(java.util.Set)} method to persist them.
     *
     * @return set of classnames, empty if no class attribute
     */
    public Set<String> classNames() {
        String[] names = classSplit.split(className());
        Set<String> classNames = new LinkedHashSet<>(Arrays.asList(names));
        classNames.remove(""); // if classNames() was empty, would include an empty class

        return classNames;
    }

    /**
     * Set the element's {@code class} attribute to the supplied class names.
     *
     * @param classNames set of classes
     * @return this element, for chaining
     */
    public ElementModel classNames(Set<String> classNames) {
        Validate.notNull(classNames);
        if (classNames.isEmpty()) {
            attributes().remove("class");
        } else {
            attributes().put("class", StringUtil.join(classNames, " "));
        }
        return this;
    }

    /**
     * Tests if this element has a class. Case insensitive.
     *
     * @param className name of class to check for
     * @return true if it does, false if not
     */
    // performance sensitive
    public boolean hasClass(String className) {
        final String classAttr = attributes().getIgnoreCase("class");
        final int len = classAttr.length();
        final int wantLen = className.length();

        if (len == 0 || len < wantLen) {
            return false;
        }

        // if both lengths are equal, only need compare the className with the attribute
        if (len == wantLen) {
            return className.equalsIgnoreCase(classAttr);
        }

        // otherwise, scan for whitespace and compare regions (with no string or arraylist allocations)
        boolean inClass = false;
        int start = 0;
        for (int i = 0; i < len; i++) {
            if (Character.isWhitespace(classAttr.charAt(i))) {
                if (inClass) {
                    // white space ends a class name, compare it with the requested one, ignore case
                    if (i - start == wantLen && classAttr.regionMatches(true, start, className, 0, wantLen)) {
                        return true;
                    }
                    inClass = false;
                }
            } else {
                if (!inClass) {
                    // we're in a class name : keep the start of the substring
                    inClass = true;
                    start = i;
                }
            }
        }

        // check the last entry
        if (inClass && len - start == wantLen) {
            return classAttr.regionMatches(true, start, className, 0, wantLen);
        }

        return false;
    }

    /**
     * Add a class name to this element's {@code class} attribute.
     *
     * @param className class name to add
     * @return this element
     */
    public ElementModel addClass(String className) {
        Validate.notNull(className);

        Set<String> classes = classNames();
        classes.add(className);
        classNames(classes);

        return this;
    }

    /**
     * Remove a class name from this element's {@code class} attribute.
     *
     * @param className class name to remove
     * @return this element
     */
    public ElementModel removeClass(String className) {
        Validate.notNull(className);

        Set<String> classes = classNames();
        classes.remove(className);
        classNames(classes);

        return this;
    }

    /**
     * Toggle a class name on this element's {@code class} attribute: if present, remove it; otherwise add it.
     *
     * @param className class name to toggle
     * @return this element
     */
    public ElementModel toggleClass(String className) {
        Validate.notNull(className);

        Set<String> classes = classNames();
        if (classes.contains(className))
            classes.remove(className);
        else
            classes.add(className);
        classNames(classes);

        return this;
    }

    /**
     * Get the value of a form element (input, textarea, etc).
     *
     * @return the value of the form element, or empty string if not set.
     */
    public String val() {
        if (tagName().equals("textarea"))
            return text();
        else
            return attr("value");
    }

    /**
     * Set the value of a form element (input, textarea, etc).
     *
     * @param value value to set
     * @return this element (for chaining)
     */
    public ElementModel val(String value) {
        if (tagName().equals("textarea"))
            text(value);
        else
            attr("value", value);
        return this;
    }

    void outerHtmlHead(final Appendable accum, int depth, final DocumentModel.OutputSettings out) throws IOException {
        if (out.prettyPrint() && (tag.formatAsBlock() || (parent() != null && parent().tag().formatAsBlock()) || out.outline())) {
            if (accum instanceof StringBuilder) {
                if (((StringBuilder) accum).length() > 0)
                    indent(accum, depth, out);
            } else {
                indent(accum, depth, out);
            }
        }
        accum.append('<').append(tagName());
        if (attributes != null) attributes.html(accum, out);

        // selfclosing includes unknown tags, isEmpty defines tags that are always empty
        if (childNodes.isEmpty() && tag.isSelfClosing()) {
            if (out.syntax() == DocumentModel.OutputSettings.Syntax.html && tag.isEmpty())
                accum.append('>');
            else
                accum.append(" />"); // <img> in html, <img /> in xml
        } else
            accum.append('>');
    }

    void outerHtmlTail(Appendable accum, int depth, DocumentModel.OutputSettings out) throws IOException {
        if (!(childNodes.isEmpty() && tag.isSelfClosing())) {
            if (out.prettyPrint() && (!childNodes.isEmpty() && (
                    tag.formatAsBlock() || (out.outline() && (childNodes.size() > 1 || (childNodes.size() == 1 && !(childNodes.get(0) instanceof TextNodeModel))))
            )))
                indent(accum, depth, out);
            accum.append("</").append(tagName()).append('>');
        }
    }

    /**
     * Retrieves the element's inner HTML. E.g. on a {@code <div>} with one empty {@code <p>}, would return
     * {@code <p></p>}. (Whereas {@link #outerHtml()} would return {@code <div><p></p></div>}.)
     *
     * @return String of HTML.
     * @see #outerHtml()
     */
    public String html() {
        StringBuilder accum = StringUtil.borrowBuilder();
        html(accum);
        String html = StringUtil.releaseBuilder(accum);
        return NodeModelUtils.outputSettings(this).prettyPrint() ? html.trim() : html;
    }

    @Override
    public <T extends Appendable> T html(T appendable) {
        final int size = childNodes.size();
        for (int i = 0; i < size; i++)
            childNodes.get(i).outerHtml(appendable);

        return appendable;
    }

    /**
     * Set this element's inner HTML. Clears the existing HTML first.
     *
     * @param html HTML to parse and set into this element
     * @return this element
     * @see #append(String)
     */
    public ElementModel html(String html) {
        empty();
        append(html);
        return this;
    }

    @Override
    public ElementModel clone() {
        return (ElementModel) super.clone();
    }

    @Override
    public ElementModel shallowClone() {
        // simpler than implementing a clone version with no child copy
        return new ElementModel(tag, baseUri, attributes);
    }
    
    @Override
    protected ElementModel doClone(NodeModel parent) {
        ElementModel clone = (ElementModel) super.doClone(parent);
        clone.attributes = attributes != null ? attributes.clone() : null;
        clone.baseUri = baseUri;
        clone.childNodes = new NodeList(clone, childNodes.size());
        clone.childNodes.addAll(childNodes); // the children then get iterated and cloned in Node.clone

        return clone;
    }

    private static final class NodeList extends ChangeNotifyingArrayList<NodeModel> {
        private final ElementModel owner;

        NodeList(ElementModel owner, int initialCapacity) {
            super(initialCapacity);
            this.owner = owner;
        }

        public void onContentsChanged() {
            owner.nodelistChanged();
        }
    }

    public void walkElementsTree(Consumer<ElementModel> consumer){
        consumer.accept(this);
        children().forEach(c -> c.walkElementsTree(consumer));
    }
}
