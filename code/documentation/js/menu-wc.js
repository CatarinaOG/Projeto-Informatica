'use strict';

customElements.define('compodoc-menu', class extends HTMLElement {
    constructor() {
        super();
        this.isNormalMode = this.getAttribute('mode') === 'normal';
    }

    connectedCallback() {
        this.render(this.isNormalMode);
    }

    render(isNormalMode) {
        let tp = lithtml.html(`
        <nav>
            <ul class="list">
                <li class="title">
                    <a href="index.html" data-type="index-link">diabo documentation</a>
                </li>

                <li class="divider"></li>
                ${ isNormalMode ? `<div id="book-search-input" role="search"><input type="text" placeholder="Type to search"></div>` : '' }
                <li class="chapter">
                    <a data-type="chapter-link" href="index.html"><span class="icon ion-ios-home"></span>Getting started</a>
                    <ul class="links">
                        <li class="link">
                            <a href="overview.html" data-type="chapter-link">
                                <span class="icon ion-ios-keypad"></span>Overview
                            </a>
                        </li>
                        <li class="link">
                            <a href="index.html" data-type="chapter-link">
                                <span class="icon ion-ios-paper"></span>README
                            </a>
                        </li>
                                <li class="link">
                                    <a href="dependencies.html" data-type="chapter-link">
                                        <span class="icon ion-ios-list"></span>Dependencies
                                    </a>
                                </li>
                                <li class="link">
                                    <a href="properties.html" data-type="chapter-link">
                                        <span class="icon ion-ios-apps"></span>Properties
                                    </a>
                                </li>
                    </ul>
                </li>
                    <li class="chapter modules">
                        <a data-type="chapter-link" href="modules.html">
                            <div class="menu-toggler linked" data-bs-toggle="collapse" ${ isNormalMode ?
                                'data-bs-target="#modules-links"' : 'data-bs-target="#xs-modules-links"' }>
                                <span class="icon ion-ios-archive"></span>
                                <span class="link-name">Modules</span>
                                <span class="icon ion-ios-arrow-down"></span>
                            </div>
                        </a>
                        <ul class="links collapse " ${ isNormalMode ? 'id="modules-links"' : 'id="xs-modules-links"' }>
                            <li class="link">
                                <a href="modules/AppModule.html" data-type="entity-link" >AppModule</a>
                                <li class="chapter inner">
                                    <div class="simple menu-toggler" data-bs-toggle="collapse" ${ isNormalMode ?
                                        'data-bs-target="#injectables-links-module-AppModule-b67ebce88b78b2fc55f19d1580f55613116dbbda33b587e5502fa4d29524a30fc256939fa9df063c1c7ab80e43373a7344ed0179cfc70ca412885aabb10ea65d"' : 'data-bs-target="#xs-injectables-links-module-AppModule-b67ebce88b78b2fc55f19d1580f55613116dbbda33b587e5502fa4d29524a30fc256939fa9df063c1c7ab80e43373a7344ed0179cfc70ca412885aabb10ea65d"' }>
                                        <span class="icon ion-md-arrow-round-down"></span>
                                        <span>Injectables</span>
                                        <span class="icon ion-ios-arrow-down"></span>
                                    </div>
                                    <ul class="links collapse" ${ isNormalMode ? 'id="injectables-links-module-AppModule-b67ebce88b78b2fc55f19d1580f55613116dbbda33b587e5502fa4d29524a30fc256939fa9df063c1c7ab80e43373a7344ed0179cfc70ca412885aabb10ea65d"' :
                                        'id="xs-injectables-links-module-AppModule-b67ebce88b78b2fc55f19d1580f55613116dbbda33b587e5502fa4d29524a30fc256939fa9df063c1c7ab80e43373a7344ed0179cfc70ca412885aabb10ea65d"' }>
                                        <li class="link">
                                            <a href="injectables/EditCellService.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >EditCellService</a>
                                        </li>
                                        <li class="link">
                                            <a href="injectables/TourService.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >TourService</a>
                                        </li>
                                    </ul>
                                </li>
                            </li>
                            <li class="link">
                                <a href="modules/AppRoutingModule.html" data-type="entity-link" >AppRoutingModule</a>
                            </li>
                            <li class="link">
                                <a href="modules/ChangesPageModule.html" data-type="entity-link" >ChangesPageModule</a>
                                    <li class="chapter inner">
                                        <div class="simple menu-toggler" data-bs-toggle="collapse" ${ isNormalMode ?
                                            'data-bs-target="#components-links-module-ChangesPageModule-a986be7aa08684984ea097c21a09bc44a9ab531c3d4de378aa7ff115605ca95a67008e3e26074d11265f3df7718c14d2c475a7c36a1bf4763e2a529f83b1c874"' : 'data-bs-target="#xs-components-links-module-ChangesPageModule-a986be7aa08684984ea097c21a09bc44a9ab531c3d4de378aa7ff115605ca95a67008e3e26074d11265f3df7718c14d2c475a7c36a1bf4763e2a529f83b1c874"' }>
                                            <span class="icon ion-md-cog"></span>
                                            <span>Components</span>
                                            <span class="icon ion-ios-arrow-down"></span>
                                        </div>
                                        <ul class="links collapse" ${ isNormalMode ? 'id="components-links-module-ChangesPageModule-a986be7aa08684984ea097c21a09bc44a9ab531c3d4de378aa7ff115605ca95a67008e3e26074d11265f3df7718c14d2c475a7c36a1bf4763e2a529f83b1c874"' :
                                            'id="xs-components-links-module-ChangesPageModule-a986be7aa08684984ea097c21a09bc44a9ab531c3d4de378aa7ff115605ca95a67008e3e26074d11265f3df7718c14d2c475a7c36a1bf4763e2a529f83b1c874"' }>
                                            <li class="link">
                                                <a href="components/ChangesComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >ChangesComponent</a>
                                            </li>
                                        </ul>
                                    </li>
                            </li>
                            <li class="link">
                                <a href="modules/EntityRoutingModule.html" data-type="entity-link" >EntityRoutingModule</a>
                            </li>
                            <li class="link">
                                <a href="modules/FlaggedMaterialModule.html" data-type="entity-link" >FlaggedMaterialModule</a>
                                    <li class="chapter inner">
                                        <div class="simple menu-toggler" data-bs-toggle="collapse" ${ isNormalMode ?
                                            'data-bs-target="#components-links-module-FlaggedMaterialModule-e04ffdaec32adefb69286728f102e02703486de9aae3d94be39aa32089229a28a269288848fc520e7e5830bca4e28dbe67136500f9051e9acb18c22954cfdfab"' : 'data-bs-target="#xs-components-links-module-FlaggedMaterialModule-e04ffdaec32adefb69286728f102e02703486de9aae3d94be39aa32089229a28a269288848fc520e7e5830bca4e28dbe67136500f9051e9acb18c22954cfdfab"' }>
                                            <span class="icon ion-md-cog"></span>
                                            <span>Components</span>
                                            <span class="icon ion-ios-arrow-down"></span>
                                        </div>
                                        <ul class="links collapse" ${ isNormalMode ? 'id="components-links-module-FlaggedMaterialModule-e04ffdaec32adefb69286728f102e02703486de9aae3d94be39aa32089229a28a269288848fc520e7e5830bca4e28dbe67136500f9051e9acb18c22954cfdfab"' :
                                            'id="xs-components-links-module-FlaggedMaterialModule-e04ffdaec32adefb69286728f102e02703486de9aae3d94be39aa32089229a28a269288848fc520e7e5830bca4e28dbe67136500f9051e9acb18c22954cfdfab"' }>
                                            <li class="link">
                                                <a href="components/FlaggedMaterialComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >FlaggedMaterialComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/FlaggedMaterialUpdateComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >FlaggedMaterialUpdateComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/FlaggedOptionsBarComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >FlaggedOptionsBarComponent</a>
                                            </li>
                                        </ul>
                                    </li>
                            </li>
                            <li class="link">
                                <a href="modules/FlaggedMaterialRoutingModule.html" data-type="entity-link" >FlaggedMaterialRoutingModule</a>
                            </li>
                            <li class="link">
                                <a href="modules/MaterialModule.html" data-type="entity-link" >MaterialModule</a>
                                    <li class="chapter inner">
                                        <div class="simple menu-toggler" data-bs-toggle="collapse" ${ isNormalMode ?
                                            'data-bs-target="#components-links-module-MaterialModule-95c948012b6565bbe1f880f8add01cee963e74b2f3b5d52a70db07b837aa68c2e1c4f4a6838540f3b549290453d875283b2738b51e67e6cf143f6eb1d2db113b"' : 'data-bs-target="#xs-components-links-module-MaterialModule-95c948012b6565bbe1f880f8add01cee963e74b2f3b5d52a70db07b837aa68c2e1c4f4a6838540f3b549290453d875283b2738b51e67e6cf143f6eb1d2db113b"' }>
                                            <span class="icon ion-md-cog"></span>
                                            <span>Components</span>
                                            <span class="icon ion-ios-arrow-down"></span>
                                        </div>
                                        <ul class="links collapse" ${ isNormalMode ? 'id="components-links-module-MaterialModule-95c948012b6565bbe1f880f8add01cee963e74b2f3b5d52a70db07b837aa68c2e1c4f4a6838540f3b549290453d875283b2738b51e67e6cf143f6eb1d2db113b"' :
                                            'id="xs-components-links-module-MaterialModule-95c948012b6565bbe1f880f8add01cee963e74b2f3b5d52a70db07b837aa68c2e1c4f4a6838540f3b549290453d875283b2738b51e67e6cf143f6eb1d2db113b"' }>
                                            <li class="link">
                                                <a href="components/CommentModalComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >CommentModalComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/CurrencyToolTipCellComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >CurrencyToolTipCellComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/FilterDisplayCellComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >FilterDisplayCellComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/FilterFormComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >FilterFormComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/FlagModalComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >FlagModalComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/HeaderGroupComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >HeaderGroupComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/MaterialComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >MaterialComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/OptionsBarComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >OptionsBarComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/SubmitModalComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >SubmitModalComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/ValueCellComponent.html" data-type="entity-link" data-context="sub-entity" data-context-id="modules" >ValueCellComponent</a>
                                            </li>
                                        </ul>
                                    </li>
                            </li>
                </ul>
                </li>
                        <li class="chapter">
                            <div class="simple menu-toggler" data-bs-toggle="collapse" ${ isNormalMode ? 'data-bs-target="#injectables-links"' :
                                'data-bs-target="#xs-injectables-links"' }>
                                <span class="icon ion-md-arrow-round-down"></span>
                                <span>Injectables</span>
                                <span class="icon ion-ios-arrow-down"></span>
                            </div>
                            <ul class="links collapse " ${ isNormalMode ? 'id="injectables-links"' : 'id="xs-injectables-links"' }>
                                <li class="link">
                                    <a href="injectables/EditCellService.html" data-type="entity-link" >EditCellService</a>
                                </li>
                                <li class="link">
                                    <a href="injectables/FlaggedMaterialFormService.html" data-type="entity-link" >FlaggedMaterialFormService</a>
                                </li>
                                <li class="link">
                                    <a href="injectables/FlaggedMaterialService.html" data-type="entity-link" >FlaggedMaterialService</a>
                                </li>
                                <li class="link">
                                    <a href="injectables/MaterialService.html" data-type="entity-link" >MaterialService</a>
                                </li>
                                <li class="link">
                                    <a href="injectables/TourService.html" data-type="entity-link" >TourService</a>
                                </li>
                            </ul>
                        </li>
                    <li class="chapter">
                        <div class="simple menu-toggler" data-bs-toggle="collapse" ${ isNormalMode ? 'data-bs-target="#guards-links"' :
                            'data-bs-target="#xs-guards-links"' }>
                            <span class="icon ion-ios-lock"></span>
                            <span>Guards</span>
                            <span class="icon ion-ios-arrow-down"></span>
                        </div>
                        <ul class="links collapse " ${ isNormalMode ? 'id="guards-links"' : 'id="xs-guards-links"' }>
                            <li class="link">
                                <a href="guards/FlaggedMaterialRoutingResolveService.html" data-type="entity-link" >FlaggedMaterialRoutingResolveService</a>
                            </li>
                        </ul>
                    </li>
                    <li class="chapter">
                        <div class="simple menu-toggler" data-bs-toggle="collapse" ${ isNormalMode ? 'data-bs-target="#interfaces-links"' :
                            'data-bs-target="#xs-interfaces-links"' }>
                            <span class="icon ion-md-information-circle-outline"></span>
                            <span>Interfaces</span>
                            <span class="icon ion-ios-arrow-down"></span>
                        </div>
                        <ul class="links collapse " ${ isNormalMode ? ' id="interfaces-links"' : 'id="xs-interfaces-links"' }>
                            <li class="link">
                                <a href="interfaces/IEditCell.html" data-type="entity-link" >IEditCell</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/IFlaggedMaterial.html" data-type="entity-link" >IFlaggedMaterial</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/IHistoryEntity.html" data-type="entity-link" >IHistoryEntity</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/IMaterial.html" data-type="entity-link" >IMaterial</a>
                            </li>
                            <li class="link">
                                <a href="interfaces/specialFilter.html" data-type="entity-link" >specialFilter</a>
                            </li>
                        </ul>
                    </li>
                    <li class="chapter">
                        <div class="simple menu-toggler" data-bs-toggle="collapse" ${ isNormalMode ? 'data-bs-target="#miscellaneous-links"'
                            : 'data-bs-target="#xs-miscellaneous-links"' }>
                            <span class="icon ion-ios-cube"></span>
                            <span>Miscellaneous</span>
                            <span class="icon ion-ios-arrow-down"></span>
                        </div>
                        <ul class="links collapse " ${ isNormalMode ? 'id="miscellaneous-links"' : 'id="xs-miscellaneous-links"' }>
                            <li class="link">
                                <a href="miscellaneous/enumerations.html" data-type="entity-link">Enums</a>
                            </li>
                            <li class="link">
                                <a href="miscellaneous/typealiases.html" data-type="entity-link">Type aliases</a>
                            </li>
                            <li class="link">
                                <a href="miscellaneous/variables.html" data-type="entity-link">Variables</a>
                            </li>
                        </ul>
                    </li>
                    <li class="chapter">
                        <a data-type="chapter-link" href="coverage.html"><span class="icon ion-ios-stats"></span>Documentation coverage</a>
                    </li>
                    <li class="divider"></li>
                    <li class="copyright">
                        Documentation generated using <a href="https://compodoc.app/" target="_blank" rel="noopener noreferrer">
                            <img data-src="images/compodoc-vectorise.png" class="img-responsive" data-type="compodoc-logo">
                        </a>
                    </li>
            </ul>
        </nav>
        `);
        this.innerHTML = tp.strings;
    }
});