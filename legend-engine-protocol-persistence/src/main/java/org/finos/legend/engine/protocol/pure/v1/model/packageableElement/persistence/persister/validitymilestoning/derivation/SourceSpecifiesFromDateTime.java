package org.finos.legend.engine.protocol.pure.v1.model.packageableElement.persistence.persister.validitymilestoning.derivation;

public class SourceSpecifiesFromDateTime extends ValidityDerivation
{
    public String sourceDateTimeFromField;

    public <T> T accept(ValidityDerivationVisitor<T> visitor)
    {
        return visitor.visit(this);
    }
}