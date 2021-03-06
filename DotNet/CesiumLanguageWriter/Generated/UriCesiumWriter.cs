// This file was generated automatically by GenerateFromSchema.  Do NOT edit it.
// https://github.com/AnalyticalGraphicsInc/czml-writer

using CesiumLanguageWriter.Advanced;
using System;
using System.Drawing;

namespace CesiumLanguageWriter
{
    /// <summary>
    /// Writes a <c>Uri</c> to a <see cref="CesiumOutputStream" />.  A <c>Uri</c> is a URI value.  The URI can optionally vary with time.
    /// </summary>
    public class UriCesiumWriter : CesiumPropertyWriter<UriCesiumWriter>
    {
        /// <summary>
        /// The name of the <c>uri</c> property.
        /// </summary>
        public const string UriPropertyName = "uri";

        /// <summary>
        /// The name of the <c>reference</c> property.
        /// </summary>
        public const string ReferencePropertyName = "reference";

        private readonly Lazy<ICesiumValuePropertyWriter<CesiumResource>> m_asUri;
        private readonly Lazy<ICesiumValuePropertyWriter<Reference>> m_asReference;

        /// <summary>
        /// Initializes a new instance.
        /// </summary>
        public UriCesiumWriter(string propertyName)
            : base(propertyName)
        {
            m_asUri = new Lazy<ICesiumValuePropertyWriter<CesiumResource>>(CreateUriAdaptor, false);
            m_asReference = new Lazy<ICesiumValuePropertyWriter<Reference>>(CreateReferenceAdaptor, false);
        }

        /// <summary>
        /// Initializes a new instance as a copy of an existing instance.
        /// </summary>
        /// <param name="existingInstance">The existing instance to copy.</param>
        protected UriCesiumWriter(UriCesiumWriter existingInstance)
            : base(existingInstance)
        {
            m_asUri = new Lazy<ICesiumValuePropertyWriter<CesiumResource>>(CreateUriAdaptor, false);
            m_asReference = new Lazy<ICesiumValuePropertyWriter<Reference>>(CreateReferenceAdaptor, false);
        }

        /// <inheritdoc />
        public override UriCesiumWriter Clone()
        {
            return new UriCesiumWriter(this);
        }

        /// <summary>
        /// Writes the value expressed as a <c>uri</c>, which is the URI value.
        /// </summary>
        /// <param name="resource">A resource object describing external data.</param>
        public void WriteUri(CesiumResource resource)
        {
            WriteUri(resource.Uri, resource.Behavior);
        }

        /// <summary>
        /// Writes the value expressed as a <c>uri</c>, which is the URI value.
        /// </summary>
        /// <param name="uri">The URI of the data.</param>
        /// <param name="resourceBehavior">An enumeration describing how to include the URI in the document. For even more control, use the overload that takes a ICesiumUriResolver.</param>
        public void WriteUri(Uri uri, CesiumResourceBehavior resourceBehavior)
        {
            WriteUri(uri.ToString(), resourceBehavior);
        }

        /// <summary>
        /// Writes the value expressed as a <c>uri</c>, which is the URI value.
        /// </summary>
        /// <param name="uri">The URI of the data.</param>
        /// <param name="resourceBehavior">An enumeration describing how to include the URI in the document. For even more control, use the overload that takes a ICesiumUriResolver.</param>
        public void WriteUri(string uri, CesiumResourceBehavior resourceBehavior)
        {
            const string PropertyName = UriPropertyName;
            if (ForceInterval)
            {
                OpenIntervalIfNecessary();
            }
            if (IsInterval)
            {
                Output.WritePropertyName(PropertyName);
            }
            Output.WriteValue(CesiumFormattingHelper.GetResourceUri(uri, resourceBehavior));
        }

        /// <summary>
        /// Writes the value expressed as a <c>uri</c>, which is the URI value.
        /// </summary>
        /// <param name="uri">The URI of the data.  The provided ICesiumUriResolver will be used to build the final URI embedded in the document.</param>
        /// <param name="resolver">An ICesiumUriResolver used to build the final URI that will be embedded in the document.</param>
        public void WriteUri(Uri uri, ICesiumUriResolver resolver)
        {
            WriteUri(uri.ToString(), resolver);
        }

        /// <summary>
        /// Writes the value expressed as a <c>uri</c>, which is the URI value.
        /// </summary>
        /// <param name="uri">The URI of the data.  The provided ICesiumUriResolver will be used to build the final URI embedded in the document.</param>
        /// <param name="resolver">An ICesiumUriResolver used to build the final URI that will be embedded in the document.</param>
        public void WriteUri(string uri, ICesiumUriResolver resolver)
        {
            const string PropertyName = UriPropertyName;
            if (ForceInterval)
            {
                OpenIntervalIfNecessary();
            }
            if (IsInterval)
            {
                Output.WritePropertyName(PropertyName);
            }
            Output.WriteValue(resolver.ResolveUri(uri));
        }

        /// <summary>
        /// Writes the value expressed as a <c>uri</c>, which is the URI value.
        /// </summary>
        /// <param name="image">The image.  A data URI will be created for this image, using PNG encoding.</param>
        public void WriteUri(Image image)
        {
            WriteUri(image, CesiumImageFormat.Png);
        }

        /// <summary>
        /// Writes the value expressed as a <c>uri</c>, which is the URI value.
        /// </summary>
        /// <param name="image">The image.  A data URI will be created for this image.</param>
        /// <param name="imageFormat">The image format to use to encode the image in the data URI.</param>
        public void WriteUri(Image image, CesiumImageFormat imageFormat)
        {
            const string PropertyName = UriPropertyName;
            if (ForceInterval)
            {
                OpenIntervalIfNecessary();
            }
            if (IsInterval)
            {
                Output.WritePropertyName(PropertyName);
            }
            Output.WriteValue(CesiumFormattingHelper.ImageToDataUri(image, imageFormat));
        }

        /// <summary>
        /// Writes the value expressed as a <c>reference</c>, which is the URI specified as a reference to another property.
        /// </summary>
        /// <param name="value">The reference.</param>
        public void WriteReference(Reference value)
        {
            const string PropertyName = ReferencePropertyName;
            OpenIntervalIfNecessary();
            Output.WritePropertyName(PropertyName);
            CesiumWritingHelper.WriteReference(Output, value);
        }

        /// <summary>
        /// Writes the value expressed as a <c>reference</c>, which is the URI specified as a reference to another property.
        /// </summary>
        /// <param name="value">The earliest date of the interval.</param>
        public void WriteReference(string value)
        {
            const string PropertyName = ReferencePropertyName;
            OpenIntervalIfNecessary();
            Output.WritePropertyName(PropertyName);
            CesiumWritingHelper.WriteReference(Output, value);
        }

        /// <summary>
        /// Writes the value expressed as a <c>reference</c>, which is the URI specified as a reference to another property.
        /// </summary>
        /// <param name="identifier">The identifier of the object which contains the referenced property.</param>
        /// <param name="propertyName">The property on the referenced object.</param>
        public void WriteReference(string identifier, string propertyName)
        {
            const string PropertyName = ReferencePropertyName;
            OpenIntervalIfNecessary();
            Output.WritePropertyName(PropertyName);
            CesiumWritingHelper.WriteReference(Output, identifier, propertyName);
        }

        /// <summary>
        /// Writes the value expressed as a <c>reference</c>, which is the URI specified as a reference to another property.
        /// </summary>
        /// <param name="identifier">The identifier of the object which contains the referenced property.</param>
        /// <param name="propertyNames">The hierarchy of properties to be indexed on the referenced object.</param>
        public void WriteReference(string identifier, string[] propertyNames)
        {
            const string PropertyName = ReferencePropertyName;
            OpenIntervalIfNecessary();
            Output.WritePropertyName(PropertyName);
            CesiumWritingHelper.WriteReference(Output, identifier, propertyNames);
        }

        /// <summary>
        /// Returns a wrapper for this instance that implements <see cref="ICesiumValuePropertyWriter{T}" /> to write a value in <c>Uri</c> format.  Because the returned instance is a wrapper for this instance, you may call <see cref="ICesiumElementWriter.Close" /> on either this instance or the wrapper, but you must not call it on both.
        /// </summary>
        /// <returns>The wrapper.</returns>
        public ICesiumValuePropertyWriter<CesiumResource> AsUri()
        {
            return m_asUri.Value;
        }

        private ICesiumValuePropertyWriter<CesiumResource> CreateUriAdaptor()
        {
            return new CesiumWriterAdaptor<UriCesiumWriter, CesiumResource>(this, (me, value) => me.WriteUri(value));
        }

        /// <summary>
        /// Returns a wrapper for this instance that implements <see cref="ICesiumValuePropertyWriter{T}" /> to write a value in <c>Reference</c> format.  Because the returned instance is a wrapper for this instance, you may call <see cref="ICesiumElementWriter.Close" /> on either this instance or the wrapper, but you must not call it on both.
        /// </summary>
        /// <returns>The wrapper.</returns>
        public ICesiumValuePropertyWriter<Reference> AsReference()
        {
            return m_asReference.Value;
        }

        private ICesiumValuePropertyWriter<Reference> CreateReferenceAdaptor()
        {
            return new CesiumWriterAdaptor<UriCesiumWriter, Reference>(this, (me, value) => me.WriteReference(value));
        }

    }
}
