package com.earnix.webk.runtime.dom.impl.parser;

import com.earnix.webk.runtime.dom.impl.helper.Validate;
import com.earnix.webk.runtime.dom.impl.nodes.XmlDeclarationModel;
import com.earnix.webk.runtime.dom.impl.CommentImpl;
import com.earnix.webk.runtime.dom.impl.ElementImpl;
import com.earnix.webk.runtime.dom.impl.NodeImpl;
import com.earnix.webk.runtime.dom.impl.CDATASectionImpl;
import com.earnix.webk.runtime.html.impl.DocumentImpl;
import com.earnix.webk.runtime.dom.impl.DocumentTypeImpl;
import com.earnix.webk.runtime.dom.impl.TextImpl;

import java.io.Reader;
import java.io.StringReader;
import java.util.List;

/**
 * Use the {@code XmlTreeBuilder} when you want to parse XML without any of the HTML DOM rules being applied to the
 * document.
 * <p>Usage example: {@code Document xmlDoc = Jsoup.parse(html, baseUrl, Parser.xmlParser());}</p>
 *
 * @author Jonathan Hedley
 */
public class XmlTreeBuilder extends TreeBuilder {
    ParseSettings defaultSettings() {
        return ParseSettings.preserveCase;
    }

    @Override
    protected void initialiseParse(Reader input, String baseUri, Parser parser) {
        super.initialiseParse(input, baseUri, parser);
        stack.add(doc); // place the document onto the stack. differs from HtmlTreeBuilder (not on stack)
        doc.outputSettings().syntax(DocumentImpl.OutputSettings.Syntax.xml);
    }

    DocumentImpl parse(Reader input, String baseUri) {
        return parse(input, baseUri, new Parser(this));
    }

    DocumentImpl parse(String input, String baseUri) {
        return parse(new StringReader(input), baseUri, new Parser(this));
    }

    @Override
    protected boolean process(Token token) {
        // start tag, end tag, doctype, comment, character, eof
        switch (token.type) {
            case StartTag:
                insert(token.asStartTag());
                break;
            case EndTag:
                popStackToClose(token.asEndTag());
                break;
            case Comment:
                insert(token.asComment());
                break;
            case Character:
                insert(token.asCharacter());
                break;
            case Doctype:
                insert(token.asDoctype());
                break;
            case EOF: // could put some normalisation here if desired
                break;
            default:
                Validate.fail("Unexpected token type: " + token.type);
        }
        return true;
    }

    private void insertNode(NodeImpl node) {
        currentElement().appendChild(node);
    }

    ElementImpl insert(Token.StartTag startTag) {
        Tag tag = Tag.valueOf(startTag.name(), settings);
        // todo: wonder if for xml parsing, should treat all tags as unknown? because it's not html.
        ElementImpl el = createElement(tag, baseUri, settings.normalizeAttributes(startTag.attributes));
        insertNode(el);
        if (startTag.isSelfClosing()) {
            if (!tag.isKnownTag()) // unknown tag, remember this is self closing for output. see above.
                tag.setSelfClosing();
        } else {
            stack.add(el);
        }
        return el;
    }

    void insert(Token.Comment commentToken) {
        CommentImpl comment = new CommentImpl(commentToken.getData());
        NodeImpl insert = comment;
        if (commentToken.bogus && comment.isXmlDeclaration()) {
            // xml declarations are emitted as bogus comments (which is right for html, but not xml)
            // so we do a bit of a hack and parse the data as an element to pull the attributes out
            XmlDeclarationModel decl = comment.asXmlDeclaration(); // else, we couldn't parse it as a decl, so leave as a comment
            if (decl != null)
                insert = decl;
        }
        insertNode(insert);
    }

    void insert(Token.Character token) {
        final String data = token.getData();
        insertNode(token.isCData() ? new CDATASectionImpl(data) : new TextImpl(data));
    }

    void insert(Token.Doctype d) {
        DocumentTypeImpl doctypeNode = new DocumentTypeImpl(settings.normalizeTag(d.getName()), d.getPublicIdentifier(), d.getSystemIdentifier());
        doctypeNode.setPubSysKey(d.getPubSysKey());
        insertNode(doctypeNode);
    }

    /**
     * If the stack contains an element with this tag's name, pop up the stack to remove the first occurrence. If not
     * found, skips.
     *
     * @param endTag tag to close
     */
    private void popStackToClose(Token.EndTag endTag) {
        String elName = settings.normalizeTag(endTag.tagName);
        ElementImpl firstFound = null;

        for (int pos = stack.size() - 1; pos >= 0; pos--) {
            ElementImpl next = stack.get(pos);
            if (next.nodeName().equals(elName)) {
                firstFound = next;
                break;
            }
        }
        if (firstFound == null)
            return; // not found, skip

        for (int pos = stack.size() - 1; pos >= 0; pos--) {
            ElementImpl next = stack.get(pos);
            stack.remove(pos);
            if (next == firstFound)
                break;
        }
    }


    List<NodeImpl> parseFragment(String inputFragment, String baseUri, Parser parser) {
        initialiseParse(new StringReader(inputFragment), baseUri, parser);
        runParser();
        return doc.getChildNodes();
    }

    List<NodeImpl> parseFragment(String inputFragment, ElementImpl context, String baseUri, Parser parser) {
        return parseFragment(inputFragment, baseUri, parser);
    }
}
